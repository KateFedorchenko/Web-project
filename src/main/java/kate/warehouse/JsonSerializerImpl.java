//package kate.warehouse;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONAware;
//import org.json.simple.JSONObject;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.Field;
//import java.util.Collection;
//
//public class JsonSerializerImpl implements JsonSerializer {
//    @Override
//    public String writeAsString(Object o) {
//        try {
//            return writeAsString0(o);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String writeAsString0(Object o) throws IllegalAccessException {
//        if (o == null) {
//            return null;
//        }
//        Object obj = convertToValidJSONElement(o);
//        if(obj instanceof JSONAware){
//            return ((JSONAware)obj).toJSONString();
//        } else {
//            return obj.toString();
//        }
//    }
//
//    private Object convertToValidJSONElement(Object o) throws IllegalAccessException {
//        Object json;
//        if (isCollection(o.getClass())) {
//            json = collectionToJSONArray((Collection<?>) o);
//        } else if (o.getClass().isArray()) {
//            json = arrayToJSONArray(o);
//        } else if (isPrimitive(o.getClass())) {
//            json = o;
//        } else {
//            json = toJSONObject(o);
//        }
//        return json;
//    }
//
//    private JSONObject toJSONObject(Object o) throws IllegalAccessException {
//        JSONObject jsonObj = new JSONObject();
//        Class<?> aClass = o.getClass();
//        Field[] fields = aClass.getDeclaredFields();
//        for (Field field : fields) {
//            Object fieldVal = field.get(o);
//            jsonObj.put(field.getName(), convertToValidJSONElement(fieldVal));
//        }
//        return jsonObj;
//    }
//
//    private JSONArray arrayToJSONArray(Object o) throws IllegalAccessException {
//        int size = Array.getLength(o);
//        JSONArray jsonArr = new JSONArray();
//        for (int i = 0; i < size; i++) {
//            Object element = Array.get(o, i);
//            jsonArr.add(convertToValidJSONElement(element));
//        }
//        return jsonArr;
//    }
//
//    private JSONArray collectionToJSONArray(Collection<?> collection) throws IllegalAccessException {
//        JSONArray jsonArr = new JSONArray();
//        for (Object o : collection) {
//            jsonArr.add(convertToValidJSONElement(o));
//        }
//        return jsonArr;
//    }
//
//    private boolean isCollection(Class<?> clazz) {
//        return Collection.class.isAssignableFrom(clazz);
//    }
//
//    private boolean isPrimitive(Class<?> clazz) {
//        return clazz.isPrimitive() || Number.class.isAssignableFrom(clazz) || clazz.equals(String.class);
//    }
//}
//
