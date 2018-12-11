package logs.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by sergeych on 7/4/2017.
 */
public class LogReader extends BufferedReader {
    private String patternStr = "^";
    private Pattern recordPattern = Pattern.compile(patternStr);

    private Iterator<String> iter = new Iterator<String>() {
        String nextLine = null;
        String prevLine = null;
        String nextRecord = null;

        @Override
        public boolean hasNext() {
            try {
                while(true) {
                    if (prevLine != null) {
                        nextLine = prevLine;
                        prevLine = null;
                    } else {
                        nextLine = readLine();
                    }
                    if (nextLine == null) {
                        break;
                    }
                    if (recordPattern.matcher(nextLine).find()) {
                        if (nextRecord != null) {
                            prevLine = nextLine;
                            break;
                        }
                        nextRecord = nextLine;
                    } else {
                        nextRecord += System.lineSeparator() + nextLine;
                    }
                };
                return (nextRecord != null);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

        }

        @Override
        public String next() {
            if (nextRecord != null || hasNext()) {
                String record = nextRecord;
                nextRecord = null;
                nextLine = null;
                return record;
            } else {
                throw new NoSuchElementException();
            }
        }
    };

    public LogReader(Reader in, int sz, String patternStr) {
        super(in, sz);
        this.patternStr = patternStr;
        recordPattern = Pattern.compile(patternStr);
    }

    public LogReader(Reader in, String patternStr) {
        super(in);
        this.patternStr = patternStr;
        recordPattern = Pattern.compile(patternStr);
    }

    public LogReader(Reader in) {
        super(in);
    }

    public Stream<String> records() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public String readRecord() {
       try {
           return iter.next();
       } catch (NoSuchElementException nsee) {
           return null;
       }
    }
}
