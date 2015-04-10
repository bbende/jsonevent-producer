package net.logstash.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Utility to produce log messages at various speeds and quantities.
 *
 * @author bbende
 */
public class LogProducer {

    static final Logger LOGGER = LoggerFactory.getLogger(LogProducer.class);

    static final int DEFAULT_NUM_LOGS = 100;
    static final long DEFAULT_DELAY = 100;

    public static final String ERROR = "ERROR";
    public static final String WARN = "WARN";
    public static final String INFO = "INFO";
    public static final String DEBUG = "DEBUG";
    public static final String TRACE = "TRACE";

    static final String[] LEVELS = new String[] {ERROR, WARN, INFO, DEBUG, TRACE};

    static final String[] MESSAGES = new String[] {"Solr is cool", "NiFi rocks!", "Lucene is awesome" };

    static final Exception[] EXCEPTIONS = new Exception[] {
        new IllegalStateException("Uh-oh something went wrong"),
        new IllegalArgumentException("Invalid value"),
        new NullPointerException("Value was null")
    };

    /**
     * Produces numLogs messages with the given delay between each.
     *
     * @param numLogs
     *              number of log messages to produce
     * @param delay
     *              milliseconds between each log message
     */
    public void produce(long numLogs, long delay) {
        Random rand = new Random();
        for (int i=0; i < numLogs; i++) {
            switch(LEVELS[rand.nextInt(5)]) {
                case ERROR:
                    final Exception e = EXCEPTIONS[rand.nextInt(EXCEPTIONS.length)];
                    LOGGER.error(e.getMessage(), e);
                    break;
                case WARN:
                    LOGGER.warn(MESSAGES[rand.nextInt(MESSAGES.length)]);
                    break;
                case INFO:
                    LOGGER.info(MESSAGES[rand.nextInt(MESSAGES.length)]);
                    break;
                case DEBUG:
                    LOGGER.debug(MESSAGES[rand.nextInt(MESSAGES.length)]);
                    break;
                case TRACE:
                    LOGGER.trace(MESSAGES[rand.nextInt(MESSAGES.length)]);
                    break;
                default:
                    LOGGER.debug("Default message");
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        int numLogs = DEFAULT_NUM_LOGS;
        long delay = DEFAULT_DELAY;

        if (args.length == 2) {
            numLogs = Integer.parseInt(args[0]);
            delay = Long.parseLong(args[1]);
        }

        LogProducer producer = new LogProducer();
        producer.produce(numLogs, delay);
    }

}
