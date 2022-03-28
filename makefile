# Makefile used for testing purposes only #
# usage:
#	"	make test=<path-to-file-after-src>/testname	"
#
# complicated, i know, but i can't be bothered

lib=build
src=src

all:	rm_class
	javac -cp $(src) -d $(lib) $(src)/$(test).java
	java -cp $(lib) $(test)

rm_class:
	rm -rf build
	mkdir build