package primetalk.quizzes.lines

import org.scalacheck.{Arbitrary, Properties, Gen}
import org.scalacheck.Prop.{forAll, BooleanOperators}

/**
  * One should implement solutionDigitizer.
  */
object Lines {

  import spire.implicits._

  type Point3dDouble = Vector[Double]
  val Point3dDouble = Vector

  type Point3dInt = Vector[Int]

  case class Line(s: Point3dDouble, e: Point3dDouble)

  implicit class Point3dDoubleEx(p: Point3dDouble) {
    def floor: Point3dInt = p.map(_.toInt)
  }

  implicit class LineEx(line: Line) {

    def vector: Point3dDouble = line.e - line.s

    def length = vector.norm

    /** A function that calculates a point on the line. */
    def parametric(t: Double): Point3dDouble =
      line.s + (t *: vector)

  }

}
/**
  * A digitizer should return the sequence of cubic cells 1x1x1 that are
  * crossed by the line.
  * The following properties should be met:
  * - for any point on the line there should be a cell in the sequence
  * - all cells should be distinct
  * - the neighbour cells should be adjacent
  *
  * An example of digitizer is given.
  */
object LineDigitizer{
  import Lines._
  type Digitizer = Line => Seq[Point3dInt]

  /** An example digitizer that doesn't return all the cells.
    * It splits th line into evenly distributed points and returns every cell for each point.
    *
    * @param stepSize the distance between points
    * @return
    */
  def simpleDigitizer(stepSize:Double = 1.0): Digitizer = (line: Line) => {
    if (line.length < 0.0001)
      Seq(line.s.floor)
    else
      (for {t <- 0.0 to 1.0 by (1.0 / line.length / stepSize)}
        yield line.parametric(t).floor).toSeq

  }
  /**
    * TODO: implement
    */
  def solutionDigitizer: Digitizer = ???
}

object LineDigitizerSpecification extends Properties("Digitizer") {

  import Lines._
  import LineDigitizer._

  val coordGen = Gen.choose(-10.0, 10.0)
  val pointGen = for {
    x <- coordGen
    y <- coordGen
    z <- coordGen
  } yield Point3dDouble(x, y, z)
  val lineGen = for {
    s <- pointGen
    e <- pointGen
  } yield Line(s, e)
  type Position = Double
  val posGen = Gen.choose(0.0, 1.0): Gen[Position]
  implicit lazy val arbLine: Arbitrary[Line] = Arbitrary(lineGen)
  implicit lazy val arbPosition: Arbitrary[Position] = Arbitrary(posGen)
  val digitizer: Digitizer = simpleDigitizer(5.0)
  //  val digitizer:Digitizer = solutionDigitizer
  property("for any point on the line there should be a cell in the sequence") =
    forAll { (line: Line, position: Position) =>
      val cells = digitizer(line)
      cells.contains(line.parametric(position).floor)
    }
  property("all cells should be distinct") =
    forAll { (line: Line) =>
      val cells = digitizer(line)
      cells.toSet.size == cells.size
    }
  property("the neighbour cells should be adjacent") =
    forAll { (line: Line, i:Int) =>
      val cells = digitizer(line)
      (cells.size>1) ==> {
        import spire.implicits._
        val index = i % (cells.size-1)
        (cells(index) - cells(index+1)).map(math.abs).sum == 1
      }
    }
}
//uncomment to run with JUnit
//import org.junit.Test
//import org.scalatest.junit.JUnitSuite
//import org.scalatest.prop.Checkers
//
//class LinesSuite extends JUnitSuite with Checkers {
//  @Test
//  def testLines() = {
//    check(LinesSpecification)
//  }
//}