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
        Deque<Boolean> values = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();

        String[] tokens = condition.split("(?i)\\s+(AND|OR)\\s+");
        Matcher matcher = Pattern.compile("(?i)\\b(AND|OR)\\b").matcher(condition);
        List<String> ops = new ArrayList<>();
        while (matcher.find()) {
            ops.add(matcher.group(1).toUpperCase());
        }

        for (int i = 0; i < tokens.length; i++) {
            boolean currentValue = evaluateSimpleCondition(row, tokens[i].trim());
            values.push(currentValue);

            if (i < ops.size()) {
                String currentOperator = ops.get(i);
                while (!operators.isEmpty() && precedence(currentOperator) <= precedence(operators.peek())) {
                    applyOperator(values, operators.pop());
                }
                operators.push(currentOperator);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(values, operators.pop());
        }

        return values.pop();
    }

    private boolean evaluateSimpleCondition(String[] row, String condition) {
        String[] parts = condition.split("(?<=[=<>!])|(?=[=<>!])");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }

        String column = parts[0].trim();
        String operator = parts[1].trim();
        String value = parts[2].trim();

        int colIndex = columnIndexMap.getOrDefault(column, -1);
        if (colIndex == -1) {
            throw new IllegalArgumentException("Column not found: " + column);
        }

        String cellValue = row[colIndex];
        return compare(cellValue, operator, value);
    }

    private boolean compare(String cellValue, String operator, String value) {
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
}
