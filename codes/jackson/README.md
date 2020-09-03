jackson主要包含了3个模块：
* jackson-core
* jackson-annotations
* jackson-databind

其中，jackson-databind依赖于jackson-annotations，jackson-annotations又依赖于jackson-core。


jackson有三种方式处理json：
1. 使用底层的基于Stream的方式对json的每一个小的组成部分进行控制
2. 使用Tree Model，通过jsonNode处理单个json节点
3. 使用databind模块，直接对Java对象进行序列化和反序列化

    通常来说，我们在日常开发中使用的是第3种方式，有时为了简便也会使用第2种方式，比如你要从一个很大
的json对象中只读取那么一两个字段的时候，采用databind方式显得有些重，JsonNode反而更简单。


在默认情况下，ObjectMapper在序列化时，将所有的字段一一序列化，无论这些字段是否有值，或者为null。

另外，序列化依赖于getter方法，如果某个字段没有getter方法，那么该字段是不会被序列化的。由此可见，
在序列化时，OjbectMapper是通过反射机制找到了对应的getter，然后将getter方法对应的字段序列化到
json中。请注意，此时ObjectMapper并不真正地检查getter对应的属性是否存在于User对象上，而是通
过getter的命名规约进行调用，比如对于getAbc()方法：

public String getAbc() {
    return "this is abc";
}

即便User上没有abc属性，abc也会被序列化：

{"name":"scott","age":20,"gender":null,"abc":"this is abc"}


将string反序列化成对象时，如果少了某些字段，程序依然正常工作，只是少了的字段的值为null。

如果string中多了某些字段，则反序列化时，会报错。说在User对象上找不到对应的属性，但是如果我们在
User上增加一个空的setter，那么此时运行成功，由此可见OjbectMapper是通过反射的机制，通过调用
json中字段所对应的setter方法进行反序列化的。并且此时，依赖于User上有默认构造函数。

反序列化时处理多余字段
在默认情况下，如果json数据中有多余的字段，那么在反序列化时jackson发现无法找到对应的对象字段，
便会抛出UnrecognizedPropertyException: Unrecognized field xxx异常，此时可以做如下配置：
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    综上，在默认情况下（即不对ObjectMapper做任何额外配置，也不对Java对象加任何Annotation），
ObjectMapper依赖于Java对象的默认无参构造函数进行反序列化，并且严格地通过getter和setter的命名
规约进行序列化和反序列化。


去除getter和setter
纯粹地为了技术方面的原因而添加getter和setter是不好的，可以通过以下方式去除掉对getter和setter的依赖：

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

ObjectMapper将通过反射机制直接操作Java对象上的字段。


忽略字段
@JsonIgnore用于字段上，表示该字段在序列化和反序列化的时候都将被忽略。

@JsonIgnoreProperties主要用于类上：
        @JsonIgnoreProperties(value = {"id","name"},ignoreUnknown = true)

表示对于id和name字段，反序列化和序列化均忽略，而对于json中存在的未知字段，在反序列化时忽略，
ignoreUnknown不对序列化起效。

序列化时排除null
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {}


ObjectMapper的配置示例：
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

