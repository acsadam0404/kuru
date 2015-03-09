import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
 
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.ERROR
 
appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
	pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n"
  }
}
 
logger("co.uk.mcb", INFO)
logger("com.bettingpromotion", WARN)
logger("org.hibernate.tool.hbm2ddl.SchemaExport", ERROR)
logger("bettingPromotionLogger", INFO)
root(WARN, ["STDOUT"])