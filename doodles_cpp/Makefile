CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		doodles_cpp.o

LIBS =

TARGET =	doodles_cpp.exe

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)
