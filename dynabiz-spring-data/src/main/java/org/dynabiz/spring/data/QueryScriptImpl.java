package org.dynabiz.spring.data;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryScriptImpl implements QueryScript{
    private static final String OP_AND = "$and";
    private static final String OP_OR = "$or";
    private static final String OP_GT = "$gt";
    private static final String OP_GTE = "$gte";
    private static final String OP_LT = "$lt";
    private static final String OP_LTE = "$lte";
    private static final String OP_IN = "$in";
    private static final String OP_BETWEEN = "$between";

    private String json;
    private String sql;

    private QueryScriptImpl(String script) throws QueryScriptSyntaxException {
        this.json = script;
        Node root = parse();
        sql = root.toString();
    }


    public String getScript() {
        return json;
    }

    public String getSql() {
        return sql;
    }

    public static QueryScript fromString(String script) throws QueryScriptSyntaxException {
        return new QueryScriptImpl(script);
    }

    private Node parse() throws QueryScriptSyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        Node root = new Node();
        root.operator = OP_AND;

        try {
            parse((Map<String, Object>) objectMapper.readValue(json, Object.class), root);
        } catch (IOException e) {
            throw new QueryScriptSyntaxException(e.getMessage());
        }
        return root;
    }


    private static void parse(Map<String, Object> query, Node curNode) throws QueryScriptSyntaxException {
        for (Map.Entry<String, Object> e : query.entrySet()) {
            Node nextNode = new Node();
            curNode.subNodes.add(nextNode);

            switch (e.getKey()) {
                case OP_AND:
                case OP_OR:

                    if (!(e.getValue() instanceof List))
                        throw new QueryScriptSyntaxException(
                                String.format("'%s' operator does not support object arguments", e.getKey()));

                    nextNode.operator = e.getKey();

                    for (Object item : (List) e.getValue()) {
                        if (item instanceof Map) parse((Map) item, nextNode);
                        else throw new QueryScriptSyntaxException(
                                String.format("'%s' operator does not support arguments that contains non-map items", e.getKey()));
                    }

                    break;
                case OP_IN:
                case OP_BETWEEN:

                    if (!(e.getValue() instanceof List))
                        throw new QueryScriptSyntaxException(
                                String.format("'%s' operator does not support object arguments", e.getKey()));
                    nextNode.operator = e.getKey();
                    for (Object item : (List) e.getValue()) {
                        if (item instanceof Map || item instanceof List) throw new QueryScriptSyntaxException(
                                String.format("'%s' operator does not support arguments that contains non-value items", e.getKey()));
                        else nextNode.subNodes.add(item instanceof String ? String.format("'%s'", item) : item);

                    }

                    break;
                case OP_GT:
                case OP_GTE:
                case OP_LT:
                case OP_LTE:

                    if (e.getValue() instanceof List || e.getValue() instanceof Map) {
                        throw new QueryScriptSyntaxException(
                                String.format("'%s' operator does not support array arguments", e.getKey()));
                    } else {
                        nextNode.operator = e.getKey();
                        nextNode.subNodes.add(e.getValue());
                    }
                    break;

                default:

                    nextNode.subNodes.add(e.getKey());
                    if (e.getValue() instanceof List)
                        throw new QueryScriptSyntaxException("'=' operator do not support array arguments");
                    else if (e.getValue() instanceof Map) {
                        nextNode.operator = "EXP";
                        parse((Map) e.getValue(), nextNode);
                    } else {
                        nextNode.operator = "=";
                        nextNode.subNodes.add(e.getValue() instanceof String ? String.format("'%s'", e.getValue()) : e.getValue());
                    }
                    break;
            }

        }
    }

    public static class Node {
        public String operator;
        public List<Object> subNodes = new ArrayList<>();

        public String toString() {
            return toString(null);
        }

        private String getOperationSymbol() {
            switch (operator.toLowerCase()) {
                case OP_AND:
                    return "AND";
                case OP_OR:
                    return "OR";
                case OP_GT:
                    return ">";
                case OP_GTE:
                    return ">=";
                case OP_LT:
                    return "<";
                case OP_LTE:
                    return "<=";
                case OP_IN:
                    return "IN";
                case OP_BETWEEN:
                    return "BETWEEN";
            }
            return operator;
        }

        public String toString(String expLeft) {
            String opSymbol = getOperationSymbol();
            if (operator.equals("EXP")) {
                expLeft = (String) subNodes.get(0);
                return ((Node) subNodes.get(1)).toString(expLeft);
            }
            if (subNodes.size() == 1) {
                return String.format("%s %s %s", expLeft, opSymbol, subNodes.get(0));
            }
            if (subNodes.size() >= 2) {
                String finalExpLeft = expLeft;

                switch (operator.toLowerCase()) {
                    case OP_AND:
                    case OP_OR:
                        return "(" + subNodes.stream().map(e -> {
                            if (e instanceof Node) {
                                return ((Node) e).toString(finalExpLeft);
                            }
                            return e.toString();
                        }).collect(Collectors.joining(" " + opSymbol + " ")) + ")";
                    case OP_GT:
                    case OP_GTE:
                    case OP_LT:
                    case OP_LTE:
                        return subNodes.stream().map(Object::toString).collect(Collectors.joining(" " + opSymbol + " "));
                    case OP_IN:
                        return String.format("%s %s (%s)",
                                expLeft,
                                opSymbol,
                                subNodes.stream().map(Object::toString).collect(Collectors.joining(", ")));
                    case OP_BETWEEN:
                        return String.format("%s %s %s AND %S",
                                expLeft,
                                opSymbol,
                                subNodes.get(0), subNodes.get(1));

                }
                return String.format("%s %s %s", subNodes.get(0), opSymbol, subNodes.get(1));
            }
            return "";
        }
    }
}
