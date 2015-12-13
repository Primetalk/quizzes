# quizzes

Some Scala programming quizzes

## Getting started

Create a clone of the Git repository:

    git clone https://github.com/Primetalk/quizzes.git

Change directory to quizzes/lines.

    cd quizzes/lines

Run the test

    sbt ~test

Fix it!

## Running with gradle

One may also run the test with gradle.

    cd quizzes
    ./gradlew test

However, it is necessary to uncomment the lines at
the end of file quizzes/lines/src/test/scala/primetalk/quizzes/lines/LineDigitizerSpecification.scala
