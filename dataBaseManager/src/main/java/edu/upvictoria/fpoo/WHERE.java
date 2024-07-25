package edu.upvictoria.fpoo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class WHERE {
    private Map<String, Integer> columnIndices;

    public WHERE(Map<String, Integer> columnIndices) {
        this.columnIndices = columnIndices;
    }

    public String extractCondition(String sql) {
        int whereIndex = sql.indexOf("WHERE");
        if (whereIndex == -1) {
            return "";
        }
        return sql.substring(whereIndex + 5).trim();
    }

    public String extractUpdateData(String sql) {
        int setIndex = sql.indexOf("SET");
        int whereIndex = sql.indexOf("WHERE");
        if (setIndex == -1 || whereIndex == -1) {
            return "";
        }
        return sql.substring(setIndex + 3, whereIndex).trim();
    }

    public boolean evaluateCondition(String line, String condition) {
        condition = condition.replaceAll("\\s+", " ").trim();
        return evaluateLogicalCondition(line, condition);
    }

    private boolean evaluateLogicalCondition(String line, String condition) {
        Stack<Boolean> results = new Stack<>();
        Stack<String> operators = new Stack<>();
        int index = 0;

        while (index < condition.length()) {
            if (condition.charAt(index) == '(') {
                int end = findClosingParenthesis(condition, index);
                boolean subResult = evaluateLogicalCondition(line, condition.substring(index + 1, end));
                results.push(subResult);
                index = end + 1;
            } else {
                int nextOpIndex = findNextOperatorIndex(condition, index);
                String expr = condition.substring(index, nextOpIndex).trim();
                boolean currentResult = evaluateExpression(line, expr);
                results.push(currentResult);
                if (nextOpIndex < condition.length()) {
                    String operator = condition.substring(nextOpIndex, nextOpIndex + 3).trim();
                    operators.push(operator);
                    index = nextOpIndex + 3;
                } else {
                    break;
                }
            }
        }

        return evaluateResults(results, operators);
    }

    private int findClosingParenthesis(String condition, int openIndex) {
        int closeIndex = openIndex;
        int counter = 1;
        while (counter > 0) {
            closeIndex++;
            if (condition.charAt(closeIndex) == '(') {
                counter++;
            } else if (condition.charAt(closeIndex) == ')') {
                counter--;
            }
        }
        return closeIndex;
    }

    private int findNextOperatorIndex(String condition, int startIndex) {
        int andIndex = condition.indexOf("AND", startIndex);
        int orIndex = condition.indexOf("OR", startIndex);
        if (andIndex == -1 && orIndex == -1) {
            return condition.length();
        } else if (andIndex == -1) {
            return orIndex;
        } else if (orIndex == -1) {
            return andIndex;
        } else {
            return Math.min(andIndex, orIndex);
        }
    }

    private boolean evaluateResults(Stack<Boolean> results, Stack<String> operators) {
        while (!operators.isEmpty()) {
            boolean b1 = results.pop();
            boolean b2 = results.pop();
            String op = operators.pop();
            if (op.equals("AND")) {
                results.push(b1 && b2);
            } else if (op.equals("OR")) {
                results.push(b1 || b2);
            }
        }
        return results.isEmpty() ? false : results.pop();
    }

    private boolean evaluateExpression(String line, String expression) {

        String regex = "(=|!=|>=|<=|>|<)";
        String[] parts = expression.split(regex);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Expresión inválida: " + expression);
        }

        String columnName = parts[0].trim();
        String value = parts[1].trim().replaceAll("^'|'$", "");


        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(expression);
        String operator = "";
        if (matcher.find()) {
            operator = matcher.group().trim();
        }

        if (operator.isEmpty()) {
            throw new IllegalArgumentException("Operador no soportado: '" + operator + "'");
        }


        Integer fieldIndex = columnIndices.get(columnName);
        if (fieldIndex == null) {
            throw new IllegalArgumentException("Columna no encontrada: " + columnName);
        }


        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Línea de datos vacía.");
        }

        String[] fields = line.split("\t");

        if (fieldIndex < 0 || fieldIndex >= fields.length) {
            throw new IllegalArgumentException("Índice de campo fuera de rango: " + fieldIndex + ". Línea de datos: " + line);
        }
        String fieldValue = fields[fieldIndex].trim();

        switch (operator) {
            case "=":
                return fieldValue.equals(value);
            case "!=":
                return !fieldValue.equals(value);
            case ">":
                return compare(fieldValue, value) > 0;
            case "<":
                return compare(fieldValue, value) < 0;
            case ">=":
                return compare(fieldValue, value) >= 0;
            case "<=":
                return compare(fieldValue, value) <= 0;
            default:
                throw new IllegalArgumentException("Operador no soportado: " + operator);
        }
    }


    private int compare(String a, String b) {
        try {
            return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
        } catch (NumberFormatException e) {
            return a.compareTo(b);
        }
    }
}
