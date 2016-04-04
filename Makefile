DSTDIR = bin
SRCDIR = src
PACKAGE = fractalTree

default:
	javac -d $(DSTDIR)/ -cp $(SRCDIR) $(SRCDIR)/$(PACKAGE)/MainClass.java

run:
	java -cp $(DSTDIR) $(PACKAGE)/MainClass

clean:
	rm -f $(DSTDIR)/$(PACKAGE)/*.class
