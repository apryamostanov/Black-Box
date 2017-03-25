# Black Box

This annotation (@I_black_box) adds A LOT of logging and profiling to your methods - by traversing the whole AST of the method.
If used in Error-only mode (@I_black_box("error")) - it logs only errors.

As of now it works ~3 times faster than java.util.logging, not saying about HUGE amount of boiler plate code and maintenance.

Work in progress. Version: 0.89
Readiness for usage by public: 15-th August 2017

Performance: Now performance is very good.

Intended purpose of usage:
- Credit card processing
- Financial software
- Batch processing
- OLTP
- PCI DSS, PA DSS applications
- TCP IP applications
- Working with hardware (I2C, Robotics, GPIO, drones, etc) - good to use with pi4j library!
- Troubleshooting on environments with limited access
- Perfomance issues investigations
- Forensics and legal case study in software

Newly added features:
- Statement-level debug

Features:
- Multithreaded by nature, designed to be as well used in the Web applications
- Does not accept free text from programmers
- Forces straightforward yet mindful coding of tracing in applications
- Automated usage via annotations and easy syntax
- Provides rich metadata and runtime insights for troubleshooting and investigations - such as line number (at compile-time) and code
- Separate Log files per each thread as basic principle
- Forces programmers to log sensitive data thoughtfully
- Simple and flexible run-time configuration
- Log context supported (per thread) - similar as log4j MDC
- No log levels. Each event can be enabled/disabled individually
- Future support of data audit
- Simple integration and usage of logging API
- Lightweight with small number dependenices (Groovy jars are required)
- Hierarchical method invocation logging - into XML-like files
- Support of performance profiling
- Auto-zip of log files from previous date
- Asynchronous logging (2 modes - realtime and external flush call)

Note: as Java Util Logging as well as other logging frameworks (Log4j, Backlog, etc..) are having a lot of limitations (functional and performance), there is no use of integrating them and this will not be worked upon.

Sample configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<logger mode="diagnostic">
    <file location="./LOGS/DEBUG/%DATE%_%TIME%_%USERNAME%.xml" formatter="xml_hierarchical" async="true" async_mode="flush" auto_zip="false">
        <all/>
    </file>
    <file location="./LOGS/WARNINGS_AND_ERRORS/%DATE%_%TIME%_%USERNAME%.xml" formatter="xml_hierarchical" async="true" async_mode="flush">
        <warning/>
        <error/>
    </file>
    <shell formatter="csv">
        <info/>
        <warning/>
        <error/>
    </shell>
</logger>
```
Initial code:
```Groovy
    @I_black_box
    void write_line(String i_line) {
        p_file_writer.write(i_line + System.lineSeparator())
        p_file_writer.flush()
        p_line_number++
        p_number_of_tcrs++
        p_number_of_transactions++
    }
```
After transformation:
```Groovy
    void write_line(String i_line) {
        T_logging_base_6_util l_util = new T_logging_base_6_util()
        T_logger l_logger = l_util.l()
        l_logger.log_enter_method('T_visa_recon_generator', 'write_line', 22, l_util.r(i_line, 'i_line'))
        try {
            l_logger.log_enter_statement('MethodCallExpression', 'p_file_writer.write( i_line + java.lang.System.lineSeparator())', 22)
            p_file_writer.write(l_logger.log_run_closure('argument', null, -1, {
                return l_logger.log_run_closure('argument-BinaryExpression', 'i_line + java.lang.System.lineSeparator()', 22, {
                    return i_line + l_logger.log_run_closure('MethodCallExpression', 'java.lang.System.lineSeparator()', 22, {
                        return System.lineSeparator()
                    })
                })
            }) as int)
            l_logger.log_exit_statement()
            l_logger.log_enter_statement('MethodCallExpression', 'p_file_writer.flush()', 23)
            p_file_writer.flush()
            l_logger.log_exit_statement()
            p_line_number ++
            p_number_of_tcrs ++
            p_number_of_transactions ++
        }
        catch (Throwable e_others) {
            l_logger.log_error_method('T_visa_recon_generator', 'write_line', 22, e_others, l_util.r(i_line, 'i_line'))
            throw e_others
        }
        finally {
            l_logger.log_exit_method()
        }
    }
```
