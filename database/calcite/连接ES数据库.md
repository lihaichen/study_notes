## 使用calcite连接ES数据
### 本次使用scala进行连接
sbt配置  
```
val calciteVersion = "1.29.0"
libraryDependencies += "org.apache.calcite" % "calcite-core" % s"$calciteVersion"
libraryDependencies += "org.apache.calcite" % "calcite-elasticsearch" % s"$calciteVersion"
```

### scala的代码
#### 获取连接
```
import org.apache.calcite.jdbc.CalciteConnection
import java.sql._

 val connection: Connection = DriverManager.getConnection("jdbc:calcite:lex=MYSQL")
 val calciteConnection: CalciteConnection = connection.asInstanceOf[CalciteConnection]
```
lex 支持JAVA、MYSQL、ORACLE，目前发现它是配置不同sql类型的

#### 获取 root schema

```
import org.apache.calcite.adapter.jdbc.JdbcSchema

val rootSchema: SchemaPlus = calciteConnection.getRootSchema
```
获取root schema，便于后续添加es的schema

#### 创建es的schema

```
val elasticsearchSchemaFactory: ElasticsearchSchemaFactory = new ElasticsearchSchemaFactory

  val map = new util.HashMap[String, Object] {}
  map.put("hosts", s"""["http://localhost:9200"]""")
  map.put("username", "用户名")
  map.put("password", "密码")
  map.put("index", "test")
  val lhcTestSchema = elasticsearchSchemaFactory.create(rootSchema, "ES", map)

  rootSchema.add("ES", lhcTestSchema)
```
hosts的内容为json，注意格式  
添加一个叫ES的schema，这个schema有一个test表

### 使用sql进行查询

```
val stmt: Statement = connection.createStatement()
    val res: ResultSet = stmt.executeQuery("select * from ES.test")
```
注意返回的数据是包括在 _MAP中

## 测试结论
- 目前测试发现是不支持插入操作的
- 不支持like查询
- 不支持contains查询，看源码contains会转成match查询，其他都是term查询