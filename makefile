# Makefile used for testing purposes only #
# usage:
#	"	make test_core	"
#
# complicated, i know, but i can't be bothered

lib=build
src=src
test_core=uml/testdir/Core_Test

all:	rm_class test_core
	

rm_class:
	rm -rf build
	mkdir build

test_core:
	javac -cp $(src) -d $(lib) $(src)/$(test_core).java
	java -ea -cp $(lib) $(test_core)