# Makefile for Assignment1
# Owen

JAVAC = /usr/bin/javac
.SUFFIXES: .java .class
SRCDIR = src
BINDIR = bin
SCRIPTDIR=Scripts
IMG=cat.jpg
WNDW=11


$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES =MeanFilterSerial.class MeanFilterParallel.class MedianFilterSerial.class MedianFilterParallel.class 
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)


default:	$(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

MeanFilterSerial: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterSerial Sample.jpg OutputMeanS.jpg 3
	
MeanFilterParallel: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterParallel baloon.jpg OutputMeanPC.jpg 9
        
MedianFilterSerial: $(CLASS_FILES)
	java -cp $(BINDIR) MedianFilterSerial Sample.jpg OutputMedianS.jpg 3

MedianFilterParallel: $(CLASS_FILES)
	java -cp $(BINDIR) MedianFilterParallel Sample.jpg OutputMedianP.jpg 3

run: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterSerial cat.jpg OutputMeanSCat.jpg 9
	java -cp $(BINDIR) MeanFilterParallel cat.jpg OutputMeanPCat.jpg 9
	java -cp $(BINDIR) MedianFilterSerial cat.jpg OutputMedianSCat.jpg 9
	java -cp $(BINDIR) MedianFilterParallel cat.jpg OutputMedianPCat.jpg 9

test: $(CLASS_FILES)
	java -cp $(BINDIR) MeanFilterSerial $(IMG) OutputMeanS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterSerial $(IMG) OutputMeanS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterSerial $(IMG) OutputMeanS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterSerial $(IMG) OutputMeanS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterSerial $(IMG) OutputMeanS_person.jpg $(WNDW) 

	java -cp $(BINDIR) MeanFilterParallel $(IMG) OutputMeanP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterParallel $(IMG) OutputMeanP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterParallel $(IMG) OutputMeanP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterParallel $(IMG) OutputMeanP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MeanFilterParallel $(IMG) OutputMeanP_person.jpg $(WNDW) 

	

	java -cp $(BINDIR) MedianFilterSerial $(IMG) OutputMedianS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterSerial $(IMG) OutputMedianS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterSerial $(IMG) OutputMedianS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterSerial $(IMG) OutputMedianS_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterSerial $(IMG) OutputMedianS_person.jpg $(WNDW) 

	java -cp $(BINDIR) MedianFilterParallel $(IMG) OutputMedianP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterParallel $(IMG) OutputMedianP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterParallel $(IMG) OutputMedianP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterParallel $(IMG) OutputMedianP_person.jpg $(WNDW) 
	java -cp $(BINDIR) MedianFilterParallel $(IMG) OutputMedianP_person.jpg $(WNDW) 


