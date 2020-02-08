import org.scalatest.FunSuite
import util.JSON

case class Test1(id: Int, name: String)

class JSONTest extends FunSuite {

  test("testRead") {
    assert(JSON.read[Test1]("""{"id":1,"name":"name"}""") == Test1(1, "name"))
    assert(JSON.read[List[Test1]]("""[{"id":1,"name":"name"}]""") == List(Test1(1, "name")))
  }

  test("testWrite") {
    assert(JSON.write(Test1(1, "name")) == """{"id":1,"name":"name"}""")
    assert(JSON.write(List(Test1(1, "name"))) == """[{"id":1,"name":"name"}]""")
  }

}
