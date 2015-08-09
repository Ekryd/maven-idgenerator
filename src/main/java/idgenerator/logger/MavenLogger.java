package idgenerator.logger;

/**
 * @author bjorn
 * @since 15-08-09
 */
public interface MavenLogger {
    void warn(String content);

    void info(String content);

    void error(String content);
}
