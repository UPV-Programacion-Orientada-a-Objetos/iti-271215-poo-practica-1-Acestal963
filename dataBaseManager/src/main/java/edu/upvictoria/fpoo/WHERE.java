package edu.upvictoria.fpoo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WHERE {
    private Map<String, Integer> columnIndexMap;

    public WHERE(Map<String, Integer> columnIndexMap) {
        this.columnIndexMap = columnIndexMap;
    }

    public boolean evaluateCondition(String[] row, String condition) {
        // Preprocesar la condición para manejar los paréntesis y operadores
        String processedCondition = processCondition(condition);
        return evaluateExpression(row, processedCondition);
    }

    private String processCondition(String condition) {
        // Reemplazar los operadores lógicos con el formato necesario para el análisis
        return condition.replaceAll("\\s+(AND|OR)\\s+", " $1 ")
                .replaceAll("\\s*\\(\\s*", "(")
                .replaceAll("\\s*\\)\\s*", ")");
    }

    private boolean evaluateExpression(String[] row, String condition) {
        Deque<Boolean> values = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();

        // Añadir un espacio antes de cada paréntesis para tokenización
        String conditionWithSpaces = condition.replaceAll("([()])", " $1 ");
        List<String> tokens = tokenize(conditionWithSpaces);

        for (String token : tokens) {
            token = token.trim();

            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    applyOperator(values, operators.pop());
                }
                operators.pop();  // Remove the '('
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    applyOperator(values, operators.pop());
                }
                operators.push(token);
            } else {
                boolean currentValue = evaluateSimpleCondition(row, token);
                values.push(currentValue);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(values, operators.pop());
        }

        return values.pop();
    }

    private List<String> tokenize(String condition) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\S+|[()\\s]+").matcher(condition);
        while (matcher.find()) {
            String token = matcher.group().trim();
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private boolean evaluateSimpleCondition(String[] row, String condition) {
        // Tokenizar la condición en base a operadores
        String[] parts = condition.split("(?<=[=<>!])|(?=[=<>!])");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }

        String column = parts[0].trim();
        String operator = parts[1].trim();
        String value = parts[2].trim().replaceAll("^'|'$", ""); // Remove surrounding single quotes

        // Limpiar comillas dobles
        value = value.replaceAll("^\"|\"$", "").replaceAll("\"\"", "\"");

        int colIndex = columnIndexMap.getOrDefault(column, -1);
        if (colIndex == -1) {
            System.out.println("Error: Column not found: " + column);
            return false;  // or throw an exception if preferred
        }

        String cellValue = row[colIndex].trim();
        return compare(cellValue, operator, value);
    }

    private boolean compare(String cellValue, String operator, String value) {
        // Eliminar comillas simples y dobles
        cellValue = cellValue.replaceAll("^'|'$", "").replaceAll("^\"|\"$", "").replaceAll("\"\"", "\"");
        value = value.replaceAll("^'|'$", "").replaceAll("^\"|\"$", "").replaceAll("\"\"", "\"");

        try {
            switch (operator) {
                case "=":
                    return cellValue.equals(value);
                case "!=":
                    return !cellValue.equals(value);
                case "<":
                    return Double.parseDouble(cellValue) < Double.parseDouble(value);
                case ">":
                    return Double.parseDouble(cellValue) > Double.parseDouble(value);
                case "<=":
                    return Double.parseDouble(cellValue) <= Double.parseDouble(value);
                case ">=":
                    return Double.parseDouble(cellValue) >= Double.parseDouble(value);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
            }
        } catch (NumberFormatException e) {
            // Si ocurre una excepción de formato numérico, asumimos que estamos comparando cadenas
            switch (operator) {
                case "=":
                    return cellValue.equals(value);
                case "!=":
                    return !cellValue.equals(value);
                default:
                    throw new IllegalArgumentException("Cannot compare non-numeric values with operator: " + operator);
            }
        }
    }

    private void applyOperator(Deque<Boolean> values, String operator) {
        boolean right = values.pop();
        boolean left = values.pop();
        boolean result;
        switch (operator) {
            case "AND":
                result = left && right;
                break;
            case "OR":
                result = left || right;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
        values.push(result);
    }

    private int precedence(String operator) {
        switch (operator) {
            case "AND":
                return 2;
            case "OR":
                return 1;
            default:
                return 0;
        }
    }

    private boolean isOperator(String token) {
        return token.equals("AND") || token.equals("OR");
    }
}

