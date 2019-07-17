# presto-code
PrestoQ code question

# Instructions to Run
This project uses gradle to build and run.

On Windows, run "gradlew run --args='<file-name>'"
On unix, it's about the same: "./gradlew run --args='<file-name>'"

where <file-name> is the path to the catalog information file is located.

The program will print the resulting ProductRecord objects to stdout.  This
is mainly meant as a simple example of a read-build loop.


# CI Badge
[![Codeship Status for wildwood/presto-code](https://app.codeship.com/projects/d7b117c0-8a54-0137-c948-2e6a8e925a93/status?branch=master)](https://app.codeship.com/projects/354799)

(codeship is not my ideal choice, since it has limited support for Java 8, but Java 7 is sufficient for this project.  I would lean toward Jenkins in a production environment.)
