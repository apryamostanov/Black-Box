# Black Box

Example of of usage: https://github.com/apryamostanov/CardProcessing

1. Experimental runtime prototyping and diagnostic framework
2. Next-generation logging, tracing and profiling framework

Work in progress. Version: 0.86

Performance: I have used the tools profiling capabilities to perform self-profiling.
Currently performance is slow: 3-4 milliseconds per cycle (log event, profile method) - affected by active profiling, but still slow.
Some optimizations have been done but it is nowhere comparable to normal loggers.

Intended purpose of usage:
- Credit card processing
- Financial software
- Batch processing
- OLTP
- PCI DSS, PA DSS applications
- TCP IP applications
- Working with hardware (I2C, Robotics, GPIO, drones, etc)
- Troubleshooting on environments with limited access
- Perfomance issues investigations

Features:
- Does not accept free text from programmers
- Forces straightforward yet mindful coding of tracing in applications
- Automated usage via annotations and easy syntax
- Provides rich metadata and runtime insights for troubleshooting and investigations - such as version of software, file name and line number (at compile-time)
- Separate Log files per each thread as basic principle
- Can forcefully mask any log data at run-time using config
- Forces programmers to log sensitive data thoughtfully
- Simple and flexible run-time configuration
- Log context supported (per thread) - similar as log4j MDC
- No log levels. Each event can be enabled/disabled individually
- Future support of data audit
- Can forcefully mute any log data at run-time using config
- 2 modes of operation: Warehousing (where all available data is spooled for emergency investigations) and Display (where only selected data is stored/displayed)
- Several levels of emergency tracing, displaying run-time details accordingly (performance/details can be configured)
- Simple integration and usage of logging API
- Lightweight with small number dependenices (note: Groovy runtime is required)
- Event-based spooling - a mode when debug is bufferred (up to configurable # of lines) and spooled only in case when error is encountered
- Trackable sources of debug data
1. Runtime data (whatever programmer or annotation passes to logger)
2. Context data (thread-specific global cache of data - such as IP address, session) - defined by programmer
3. Pre-defined data (Datetimestamp, Process ID, Thread Id, etc)
4. Compile-time Meta-data (line number, file name, CVS version) - in future
- Hierarchical method invocation logging
- Support of performance profiling

Sample usage:
```Groovy
    @Test
    void test_039() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_038.conf")
        String w = new T_sample_class_for_annotation_test().reissue_card("555555555555555")
    }
```
```Groovy
package com.a9ae0b01f0ffc.black_box.tests.mockup
import com.a9ae0b01f0ffc.black_box.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.exceptions.E_application_exception
class T_sample_class_for_annotation_test {
    @I_black_box
    Boolean check_card_for_reissue(T_pan_maskable i_pan) {
        T_pan l_validation_pan = new T_pan(i_pan.get_pan())
        T_s.l().log_debug(T_s.s().THE_PAN_IS_ELIGIBLE_FOR_REISSUE, T_s.t(l_validation_pan, T_s.s().l_validation_pan))
        return true
    }
    @I_black_box
    T_pan_maskable create_new_pan(T_pan_maskable i_old_pan, Boolean i_is_create_new_pan, Integer i_duration) {
        T_s.l().log_info(T_s.s().IGNORING_DURATION_FOR_NEW_PAN)
        T_pan_maskable l_new_pan = new T_pan_maskable("44444444444444444")
        check_card_for_reissue(l_new_pan)
        return l_new_pan
    }
    @I_black_box
    T_pan_maskable reissue_card(String i_old_pan) {
        T_pan_maskable l_pan = new T_pan_maskable(i_old_pan)
        T_pan_maskable l_new_pan
        if (check_card_for_reissue(l_pan)) {
            l_new_pan = create_new_pan(l_pan, true, 2)
        }
        return l_new_pan
    }
}
```

Sample configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<logger mode="diagnostic">
    <file location="c:/LOGS/WAREHOUSE/LOG_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.xml" formatter="xml_hierarchical" purpose="warehouse" mask="sensitive">
        <enter>
            <this mute="true"/>
        </enter>
        <exit/>
        <debug/>
        <info/>
        <warning/>
        <error/>
    </file>
    <shell formatter="csv" purpose="display" mask="sensitive">
        <info>
            <event/>
            <message/>
            <runtime/>
        </info>
        <warning>
            <event/>
            <message/>
            <runtime/>
        </warning>
        <error>
            <event/>
            <exception_message/>
            <exception_traces/>
        </error>
    </shell>
</logger>
```

Sample output:
```xml
<invocation class="T_sample_class_for_annotation_test" method="reissue_card">
    <arguments>
        <argument class="String" name="i_old_pan">555555555555555</argument>
    </arguments>
    <execution>
        <invocation class="T_sample_class_for_annotation_test" method="check_card_for_reissue">
            <arguments>
                <argument class="T_pan_maskable" name="i_pan">com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(555555555555555)</argument>
            </arguments>
            <execution>
                <debug message="THE_PAN_IS_ELIGIBLE_FOR_REISSUE">
                    <trace class="T_pan" name="l_validation_pan">com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(555555555555555)</trace>
                </debug>
            </execution>
        </invocation>
        <invocation class="T_sample_class_for_annotation_test" method="create_new_pan">
            <arguments>
                <argument class="T_pan_maskable" name="i_old_pan">com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(555555555555555)</argument>
                <argument class="Integer" name="i_duration">2</argument>
                <argument class="Boolean" name="i_is_create_new_pan">true</argument>
            </arguments>
            <execution>
                <info message="IGNORING_DURATION_FOR_NEW_PAN"/>
                <invocation class="T_sample_class_for_annotation_test" method="check_card_for_reissue">
                    <arguments>
                        <argument class="T_pan_maskable" name="i_pan">com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(44444444444444444)</argument>
                    </arguments>
                    <execution>
                        <debug message="THE_PAN_IS_ELIGIBLE_FOR_REISSUE">
                            <trace class="T_pan" name="l_validation_pan">com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(44444444444444444)</trace>
                        </debug>
                    </execution>
                </invocation>
            </execution>
        </invocation>
    </execution>
</invocation>
```
