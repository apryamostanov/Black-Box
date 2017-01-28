# Black Box

Logging tool written in Groovy

Work in progress.

Intended purpose of usage:
- Credit card processing
- Financial server applications
- Batch processing
- OLTP
- PCI DSS, PA DSS applications
- TCP IP applications
- Working with hardware (I2C, Robotics, GPIO, drones, etc)

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
⋅⋅*Runtime data (whatever programmer or annotation passes to logger)
⋅⋅*Context data (thread-specific global cache of data - such as IP address, session) - defined by programmer
⋅⋅*Pre-defined data (Datetimestamp, Process ID, Thread Id, etc)
⋅⋅*Compile-time Meta-data (line number, file name, CVS version)

Sample usage:
```Groovy
T_s.l().log_info(T_s.s().HELLO_WORLD)
```

Sample configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<logger mode="diagnostic">
    <file_buffer location="c:/LOGS/WAREHOUSE/LOG_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.xml" formatter="xml" purpose="warehouse" buffer="300" spool_event="error">
        <enter>
            <context mask="true"/>
            <runtime mask="true"/>
        </enter>
        <exit>
            <context mask="true"/>
            <runtime mask="true"/>
        </exit>
        <debug>
            <context mask="true"/>
            <runtime mask="true"/>
        </debug>
        <info>
            <context mask="true"/>
            <runtime mask="true"/>
        </info>
        <warning>
            <context mask="true"/>
            <runtime mask="true"/>
        </warning>
        <error>
            <context mask="true"/>
            <runtime mask="true"/>
            <exception_traces mask="true"/>
        </error>
    </file_buffer>
    <file location="c:/LOGS/DISPLAY/LOG_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.html" formatter="html" purpose="display">
        <enter>
            <datetimestamp/>
            <process/>
            <event/>
            <class/>
            <method/>
            <depth/>
            <context mask="true"/>
            <runtime mask="true"/>
        </enter>
        <exit>
            <datetimestamp/>
            <process/>
            <event/>
            <class/>
            <method/>
            <depth/>
            <context mask="true"/>
            <runtime mask="true"/>
        </exit>
        <info>
            <datetimestamp/>
            <process/>
            <event/>
            <class/>
            <method/>
            <depth/>
            <message/>
            <runtime mask="true"/>
        </info>
        <warning>
            <datetimestamp/>
            <process/>
            <event/>
            <class/>
            <method/>
            <depth/>
            <message/>
            <runtime mask="true"/>
        </warning>
        <error>
            <datetimestamp/>
            <process/>
            <event/>
            <class/>
            <method/>
            <depth/>
            <exception_message/>
            <exception_traces mask="true"/>
            <context mask="true"/>
            <runtime mask="true"/>
        </error>
    </file>
    <shell formatter="csv" purpose="display">
        <info>
            <event/>
            <message/>
            <runtime mask="true"/>
        </info>
        <warning>
            <event/>
            <message/>
            <runtime mask="true"/>
        </warning>
        <error>
            <event/>
            <exception_message/>
            <exception_traces mask="true"/>
        </error>
    </shell>
</logger>
```

Sample Warehouse log:
```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xml" href="z.xml"?>
<traces>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="enter" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:466" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="i_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="i_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="info" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:521" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="HELLO_WORLD" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="2" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="enter" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:531" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="i_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="i_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="2" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="info" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:535" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="HELLO_WORLD" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="2" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="info" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:539" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="HELLO_WORLD" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="3" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="enter" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:543" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="i_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="i_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="3" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="exit" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:547" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="result_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="result_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="3" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="enter" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:551" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="i_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="i_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
	<event>
		<trace config_class="" mask="" masked="false" muted="false" name="class" ref_class_name="class" search_name_config="class" serialized_representation="T_tests" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="method" ref_class_name="method" search_name_config="method" serialized_representation="test_028" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="depth" ref_class_name="depth" search_name_config="depth" serialized_representation="3" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="event" ref_class_name="event" search_name_config="event" serialized_representation="exit" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="datetimestamp" ref_class_name="datetimestamp" search_name_config="datetimestamp" serialized_representation="2017-01-28 21:09:58:555" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception" ref_class_name="exception" search_name_config="exception" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="exception_message" ref_class_name="exception_message" search_name_config="exception_message" serialized_representation="Trace missing" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="message" ref_class_name="message" search_name_config="message" serialized_representation="null" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="thread" ref_class_name="thread" search_name_config="thread" serialized_representation="1" source="predefined"/>
		<trace config_class="" mask="" masked="false" muted="false" name="process" ref_class_name="process" search_name_config="process" serialized_representation="12500" source="predefined"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="result_pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan" search_name_config="result_pan" serialized_representation="Trace masked" source="runtime"/>
		<trace config_class="" mask="true" masked="true" muted="false" name="pan" ref_class_name="com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable" search_name_config="pan" serialized_representation="Trace masked" source="context"/>
	</event>
</traces>
```
