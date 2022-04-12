# Makefile used for testing purposes only #
# usage:
#	"	make test_core	"
#
# complicated, i know, but i can't be bothered

lib=build
src=src
test_core=uml/testdir/Core_Test
test_io=uml/testdir/IO_Test

all: rm_class test_core add_numb
	

rm_class:
	rm -rf build
	mkdir build
	

test_core:
	javac -cp $(src) -d $(lib) $(src)/$(test_core).java
	java -ea -cp $(lib) $(test_core)


test_io:
	javac -cp $(src) -d $(lib) $(src)/$(test_io).java
	java -ea -cp $(lib) $(test_io)


rm_tmp_xml:
	rm ./aaa.xml
	rm ./bbb.xml

add_numb:
	rm -rf build
	mkdir build
	touch ./build/numb.txt

