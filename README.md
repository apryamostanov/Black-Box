# Black Box

This annotation (@I_black_box) adds A LOT of logging and profiling to your methods - by traversing the whole AST of the method.
If used in Error-only mode (@I_black_box) - it logs only errors.

As of now it works ~3 times faster than java.util.logging, not saying about HUGE amount of boiler plate code and maintenance.

Work in progress. Version: 0.88
Readiness for usage by public: 15-th August 2017

Features in progress:
- java.util.logging integration - in progress, but it seems that performance of java.util.logging is very less

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
- Multithreaded by nature, designed to be used in Web applications
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

Init (1 time per thread):
```Groovy
class Main{
    public static void main(String... i_args) {
        T_common_base_2_context.x().init_custom("./conf/commons.conf")
    }
}
```

Sample usage:
```Groovy
class Anyclass {
    @I_black_box
    Anytype anymethod(Anyarguments... args) {
        anything()
    }
}
```

Sample configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<logger mode="diagnostic">
    <file location="./LOGS/DEBUG/%DATE%_%TIME%_%USERNAME%.xml" formatter="xml_hierarchical" mask="sensitive">
        <enter/>
        <result/>
        <exit/>
        <debug/>
        <send/>
        <receive/>
        <info/>
        <warning/>
        <error/>
    </file>
    <file location="./LOGS/WARNINGS_AND_ERRORS/%DATE%_%TIME%_%USERNAME%.xml" formatter="xml_hierarchical" mask="sensitive">
        <warning/>
        <error/>
    </file>
    <shell formatter="csv" mask="sensitive">
        <info/>
        <warning/>
        <error/>
    </shell>
</logger>
```

Sample output:
```xml
<convert_vts_log_to_ctf datetimestamp="2017-03-14 23:39:44:356" line="120" exception_class="T_visa_recon_generator">
    <argument class="String" name="i_vts_log_file_name">
        ./src/com/a9ae0b01f0ffc/VSMSGEN/tests/data/vts.log
    </argument>
    <argument class="String" name="i_ctf_file_name">
        ./src/com/a9ae0b01f0ffc/VSMSGEN/tests/data/CTF/TC33_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.ctf
    </argument>
    <log_info datetimestamp="2017-03-14 23:39:44:421" line="120" exception_class="T_visa_recon_generator">
        <info datetimestamp="2017-03-14 23:39:44:427" line="120" exception_class="T_visa_recon_generator" message="Welcome to Visa VIP Full Service Recon File Generator"/>
        <elapsed_time milliseconds="16"/>
    </log_info>
    <log_info datetimestamp="2017-03-14 23:39:44:438" line="121" exception_class="T_visa_recon_generator">
        <info datetimestamp="2017-03-14 23:39:44:442" line="121" exception_class="T_visa_recon_generator" message="This tool converts VTS log file to CTF TC33 V222xx reports"/>
        <elapsed_time milliseconds="6"/>
    </log_info>
    <log_info datetimestamp="2017-03-14 23:39:44:444" line="122" exception_class="T_visa_recon_generator">
        <info datetimestamp="2017-03-14 23:39:44:447" line="122" exception_class="T_visa_recon_generator" message="Version 1.0, 5-th February 2017">
            <trace class="String" >
                1.0, 5-th February 2017
            </trace>
        </info>
        <elapsed_time milliseconds="9"/>
    </log_info>
    <BinaryExpression datetimestamp="2017-03-14 23:39:44:454" line="123" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="6"/>
    </BinaryExpression>
    <declaration datetimestamp="2017-03-14 23:39:44:459" line="124" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="8"/>
    </declaration>
    <declaration datetimestamp="2017-03-14 23:39:44:468" line="125" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="715"/>
    </declaration>
    <declaration datetimestamp="2017-03-14 23:39:45:183" line="126" exception_class="T_visa_recon_generator">
        <warning datetimestamp="2017-03-14 23:39:45:218" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700817000347:0430:000347 skipped at VTS Log file line 17267 because transaction with same key already found at lines 17169 and 17221">
            <trace class="String" >
                700817000347:0430:000347
            </trace>
            <trace class="Integer" >
                17267
            </trace>
            <trace class="Integer" >
                17169
            </trace>
            <trace class="Integer" >
                17221
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:230" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700817000347:0430:000347 skipped at VTS Log file line 17319 because transaction with same key already found at lines 17169 and 17221">
            <trace class="String" >
                700817000347:0430:000347
            </trace>
            <trace class="Integer" >
                17319
            </trace>
            <trace class="Integer" >
                17169
            </trace>
            <trace class="Integer" >
                17221
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:238" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700817000347:0430:000347 skipped at VTS Log file line 17365 because transaction with same key already found at lines 17169 and 17221">
            <trace class="String" >
                700817000347:0430:000347
            </trace>
            <trace class="Integer" >
                17365
            </trace>
            <trace class="Integer" >
                17169
            </trace>
            <trace class="Integer" >
                17221
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:245" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700817000347:0430:000347 skipped at VTS Log file line 17421 because transaction with same key already found at lines 17169 and 17221">
            <trace class="String" >
                700817000347:0430:000347
            </trace>
            <trace class="Integer" >
                17421
            </trace>
            <trace class="Integer" >
                17169
            </trace>
            <trace class="Integer" >
                17221
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:253" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700816000325:0230:000325 skipped at VTS Log file line 18697 because transaction with same key already found at lines 17468 and 17518">
            <trace class="String" >
                700816000325:0230:000325
            </trace>
            <trace class="Integer" >
                18697
            </trace>
            <trace class="Integer" >
                17468
            </trace>
            <trace class="Integer" >
                17518
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:260" line="126" exception_class="T_visa_recon_generator" message="Transaction with key RRN MTI STAN 700816000325:0230:000325 skipped at VTS Log file line 18747 because transaction with same key already found at lines 17468 and 17518">
            <trace class="String" >
                700816000325:0230:000325
            </trace>
            <trace class="Integer" >
                18747
            </trace>
            <trace class="Integer" >
                17468
            </trace>
            <trace class="Integer" >
                17518
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:276" line="126" exception_class="T_visa_recon_generator" message="Request with RRN 700709000219 at line 5601 does not have a Response in VTS log">
            <trace class="String" >
                Request
            </trace>
            <trace class="String" >
                700709000219
            </trace>
            <trace class="Integer" >
                5601
            </trace>
            <trace class="String" >
                Response
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:282" line="126" exception_class="T_visa_recon_generator" message="Request with RRN 700709000221 at line 5793 does not have a Response in VTS log">
            <trace class="String" >
                Request
            </trace>
            <trace class="String" >
                700709000221
            </trace>
            <trace class="Integer" >
                5793
            </trace>
            <trace class="String" >
                Response
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:294" line="126" exception_class="T_visa_recon_generator" message="Request with RRN 700918000389 at line 19194 does not have a Response in VTS log">
            <trace class="String" >
                Request
            </trace>
            <trace class="String" >
                700918000389
            </trace>
            <trace class="Integer" >
                19194
            </trace>
            <trace class="String" >
                Response
            </trace>
        </warning>
        <warning datetimestamp="2017-03-14 23:39:45:300" line="126" exception_class="T_visa_recon_generator" message="Request with RRN 700918000391 at line 19377 does not have a Response in VTS log">
            <trace class="String" >
                Request
            </trace>
            <trace class="String" >
                700918000391
            </trace>
            <trace class="Integer" >
                19377
            </trace>
            <trace class="String" >
                Response
            </trace>
        </warning>
        <elapsed_time milliseconds="125"/>
    </declaration>
    <declaration datetimestamp="2017-03-14 23:39:45:308" line="127" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="11"/>
    </declaration>
    <write_line datetimestamp="2017-03-14 23:39:45:319" line="128" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="9"/>
    </write_line>
    <for datetimestamp="2017-03-14 23:39:45:328" line="129" exception_class="T_visa_recon_generator">
        <iteration datetimestamp="2017-03-14 23:39:45:329" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:330" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:333" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700508000153 with request at line 11 and response at line 103">
                    <trace class="String" >
                        700508000153
                    </trace>
                    <trace class="Integer" >
                        11
                    </trace>
                    <trace class="Integer" >
                        103
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:338" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700508000153 with request at line 11 and response at line 103">
                    <trace class="String" >
                        700508000153
                    </trace>
                    <trace class="Integer" >
                        11
                    </trace>
                    <trace class="Integer" >
                        103
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:343" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700508000153 with request at line 11 and response at line 103">
                    <trace class="String" >
                        700508000153
                    </trace>
                    <trace class="Integer" >
                        11
                    </trace>
                    <trace class="Integer" >
                        103
                    </trace>
                </warning>
                <elapsed_time milliseconds="18"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:348" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:353" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:358" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:361" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:363" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:367" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:370" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="42"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:371" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:372" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:373" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700510000155 with request at line 153 and response at line 245">
                    <trace class="String" >
                        700510000155
                    </trace>
                    <trace class="Integer" >
                        153
                    </trace>
                    <trace class="Integer" >
                        245
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:377" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700510000155 with request at line 153 and response at line 245">
                    <trace class="String" >
                        700510000155
                    </trace>
                    <trace class="Integer" >
                        153
                    </trace>
                    <trace class="Integer" >
                        245
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:382" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700510000155 with request at line 153 and response at line 245">
                    <trace class="String" >
                        700510000155
                    </trace>
                    <trace class="Integer" >
                        153
                    </trace>
                    <trace class="Integer" >
                        245
                    </trace>
                </warning>
                <elapsed_time milliseconds="14"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:387" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:390" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:393" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:395" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:396" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:399" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:400" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="31"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:402" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:403" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:404" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700510000157 with request at line 295 and response at line 391">
                    <trace class="String" >
                        700510000157
                    </trace>
                    <trace class="Integer" >
                        295
                    </trace>
                    <trace class="Integer" >
                        391
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:408" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:410" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:413" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:415" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:416" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:420" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="8"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:428" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="27"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:430" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:430" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:431" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700510000159 with request at line 441 and response at line 537">
                    <trace class="String" >
                        700510000159
                    </trace>
                    <trace class="Integer" >
                        441
                    </trace>
                    <trace class="Integer" >
                        537
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:436" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:439" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:443" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:447" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:450" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:453" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:455" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="26"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:456" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:457" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:458" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700510000159 with request at line 587 and response at line 650">
                    <trace class="String" >
                        700510000159
                    </trace>
                    <trace class="Integer" >
                        587
                    </trace>
                    <trace class="Integer" >
                        650
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:462" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700510000159 with request at line 587 and response at line 650">
                    <trace class="String" >
                        700510000159
                    </trace>
                    <trace class="Integer" >
                        587
                    </trace>
                    <trace class="Integer" >
                        650
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:466" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700510000159 with request at line 587 and response at line 650">
                    <trace class="String" >
                        700510000159
                    </trace>
                    <trace class="Integer" >
                        587
                    </trace>
                    <trace class="Integer" >
                        650
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:470" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:472" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:475" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:476" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:477" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="5"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:482" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:483" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="28"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:484" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:485" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:486" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700511000161 with request at line 696 and response at line 792">
                    <trace class="String" >
                        700511000161
                    </trace>
                    <trace class="Integer" >
                        696
                    </trace>
                    <trace class="Integer" >
                        792
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:490" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:492" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:494" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:496" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:498" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:500" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:501" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:503" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:503" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:504" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700511000161 with request at line 842 and response at line 900">
                    <trace class="String" >
                        700511000161
                    </trace>
                    <trace class="Integer" >
                        842
                    </trace>
                    <trace class="Integer" >
                        900
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:507" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700511000161 with request at line 842 and response at line 900">
                    <trace class="String" >
                        700511000161
                    </trace>
                    <trace class="Integer" >
                        842
                    </trace>
                    <trace class="Integer" >
                        900
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:511" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700511000161 with request at line 842 and response at line 900">
                    <trace class="String" >
                        700511000161
                    </trace>
                    <trace class="Integer" >
                        842
                    </trace>
                    <trace class="Integer" >
                        900
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:516" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:518" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:520" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:522" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:523" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:525" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:526" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="23"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:526" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:527" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:528" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700511000163 with request at line 946 and response at line 1042">
                    <trace class="String" >
                        700511000163
                    </trace>
                    <trace class="Integer" >
                        946
                    </trace>
                    <trace class="Integer" >
                        1042
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:531" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:533" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:535" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:537" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:538" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:540" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:542" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:543" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:544" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:545" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700511000165 with request at line 1092 and response at line 1188">
                    <trace class="String" >
                        700511000165
                    </trace>
                    <trace class="Integer" >
                        1092
                    </trace>
                    <trace class="Integer" >
                        1188
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:548" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:550" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:553" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:554" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:555" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:557" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:559" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:560" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:560" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:561" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700511000167 with request at line 1238 and response at line 1334">
                    <trace class="String" >
                        700511000167
                    </trace>
                    <trace class="Integer" >
                        1238
                    </trace>
                    <trace class="Integer" >
                        1334
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:565" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:567" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:570" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:572" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:573" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:575" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:576" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:577" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:578" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:579" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700512000169 with request at line 1384 and response at line 1480">
                    <trace class="String" >
                        700512000169
                    </trace>
                    <trace class="Integer" >
                        1384
                    </trace>
                    <trace class="Integer" >
                        1480
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:582" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:584" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:586" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:587" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:588" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:590" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:591" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:592" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:593" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:594" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700513000171 with request at line 1530 and response at line 1626">
                    <trace class="String" >
                        700513000171
                    </trace>
                    <trace class="Integer" >
                        1530
                    </trace>
                    <trace class="Integer" >
                        1626
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:597" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:599" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:601" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:602" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:603" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:605" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:606" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:607" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:607" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:608" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:610" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:611" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:613" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:614" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:616" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:616" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:617" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:618" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:619" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:620" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:622" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:623" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:624" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:626" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:627" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:628" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:628" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:629" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:631" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:633" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:634" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:635" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:636" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:637" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:638" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:639" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:639" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:641" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:643" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:644" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:645" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:647" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:648" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:649" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:649" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:650" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:652" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:655" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:657" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:658" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:660" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:661" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:662" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:663" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:664" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:666" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:668" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:670" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:670" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:672" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:673" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:674" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:675" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:676" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="4"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:680" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:683" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:685" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:686" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:688" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:690" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:691" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:692" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:693" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:694" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:696" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:698" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:698" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:701" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:702" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:704" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:704" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:705" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:707" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:709" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:710" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:711" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:714" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:715" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:716" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:717" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:721" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:722" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:724" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:726" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:727" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:729" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:730" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:731" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:732" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:733" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700708000193 with request at line 3148 and response at line 3244">
                    <trace class="String" >
                        700708000193
                    </trace>
                    <trace class="Integer" >
                        3148
                    </trace>
                    <trace class="Integer" >
                        3244
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:736" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700708000193 with request at line 3148 and response at line 3244">
                    <trace class="String" >
                        700708000193
                    </trace>
                    <trace class="Integer" >
                        3148
                    </trace>
                    <trace class="Integer" >
                        3244
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:741" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:742" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:744" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:745" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:746" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:747" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:748" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:749" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:750" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:751" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700708000195 with request at line 3290 and response at line 3386">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3290
                    </trace>
                    <trace class="Integer" >
                        3386
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:755" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700708000195 with request at line 3290 and response at line 3386">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3290
                    </trace>
                    <trace class="Integer" >
                        3386
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:758" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:759" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:761" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:763" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:763" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:765" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:766" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:767" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:768" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:768" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700708000195 with request at line 3432 and response at line 3486">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3432
                    </trace>
                    <trace class="Integer" >
                        3486
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:772" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700708000195 with request at line 3432 and response at line 3486">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3432
                    </trace>
                    <trace class="Integer" >
                        3486
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:775" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700708000195 with request at line 3432 and response at line 3486">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3432
                    </trace>
                    <trace class="Integer" >
                        3486
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:778" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700708000195 with request at line 3432 and response at line 3486">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3432
                    </trace>
                    <trace class="Integer" >
                        3486
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:781" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700708000195 with request at line 3432 and response at line 3486">
                    <trace class="String" >
                        700708000195
                    </trace>
                    <trace class="Integer" >
                        3432
                    </trace>
                    <trace class="Integer" >
                        3486
                    </trace>
                </warning>
                <elapsed_time milliseconds="16"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:784" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:786" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:788" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:789" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:790" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:792" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:793" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="26"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:793" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:794" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:795" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700708000198 with request at line 3524 and response at line 3620">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3524
                    </trace>
                    <trace class="Integer" >
                        3620
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:798" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700708000198 with request at line 3524 and response at line 3620">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3524
                    </trace>
                    <trace class="Integer" >
                        3620
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:801" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:803" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:805" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:806" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:807" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:809" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:810" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:811" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:811" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:812" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700708000198 with request at line 3666 and response at line 3716">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3666
                    </trace>
                    <trace class="Integer" >
                        3716
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:815" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700708000198 with request at line 3666 and response at line 3716">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3666
                    </trace>
                    <trace class="Integer" >
                        3716
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:819" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700708000198 with request at line 3666 and response at line 3716">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3666
                    </trace>
                    <trace class="Integer" >
                        3716
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:822" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700708000198 with request at line 3666 and response at line 3716">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3666
                    </trace>
                    <trace class="Integer" >
                        3716
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:826" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700708000198 with request at line 3666 and response at line 3716">
                    <trace class="String" >
                        700708000198
                    </trace>
                    <trace class="Integer" >
                        3666
                    </trace>
                    <trace class="Integer" >
                        3716
                    </trace>
                </warning>
                <elapsed_time milliseconds="18"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:829" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:831" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:833" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:834" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:835" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="13"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:849" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:849" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="39"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:850" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:851" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:851" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000201 with request at line 3754 and response at line 3853">
                    <trace class="String" >
                        700709000201
                    </trace>
                    <trace class="Integer" >
                        3754
                    </trace>
                    <trace class="Integer" >
                        3853
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:854" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000201 with request at line 3754 and response at line 3853">
                    <trace class="String" >
                        700709000201
                    </trace>
                    <trace class="Integer" >
                        3754
                    </trace>
                    <trace class="Integer" >
                        3853
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:857" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:858" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:860" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:861" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:862" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:864" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:865" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:866" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:866" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:867" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000201 with request at line 3899 and response at line 3949">
                    <trace class="String" >
                        700709000201
                    </trace>
                    <trace class="Integer" >
                        3899
                    </trace>
                    <trace class="Integer" >
                        3949
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:870" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000201 with request at line 3899 and response at line 3949">
                    <trace class="String" >
                        700709000201
                    </trace>
                    <trace class="Integer" >
                        3899
                    </trace>
                    <trace class="Integer" >
                        3949
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:872" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000201 with request at line 3899 and response at line 3949">
                    <trace class="String" >
                        700709000201
                    </trace>
                    <trace class="Integer" >
                        3899
                    </trace>
                    <trace class="Integer" >
                        3949
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:875" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:876" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:878" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:879" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:880" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:881" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:882" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:882" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:883" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:883" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000204 with request at line 3989 and response at line 4088">
                    <trace class="String" >
                        700709000204
                    </trace>
                    <trace class="Integer" >
                        3989
                    </trace>
                    <trace class="Integer" >
                        4088
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:886" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000204 with request at line 3989 and response at line 4088">
                    <trace class="String" >
                        700709000204
                    </trace>
                    <trace class="Integer" >
                        3989
                    </trace>
                    <trace class="Integer" >
                        4088
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:889" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:890" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:892" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:893" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:894" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:896" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:896" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:897" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:897" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:898" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000204 with request at line 4134 and response at line 4184">
                    <trace class="String" >
                        700709000204
                    </trace>
                    <trace class="Integer" >
                        4134
                    </trace>
                    <trace class="Integer" >
                        4184
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:901" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000204 with request at line 4134 and response at line 4184">
                    <trace class="String" >
                        700709000204
                    </trace>
                    <trace class="Integer" >
                        4134
                    </trace>
                    <trace class="Integer" >
                        4184
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:903" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000204 with request at line 4134 and response at line 4184">
                    <trace class="String" >
                        700709000204
                    </trace>
                    <trace class="Integer" >
                        4134
                    </trace>
                    <trace class="Integer" >
                        4184
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:906" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:907" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:908" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:910" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:910" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:911" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:912" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:913" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:913" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:914" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000207 with request at line 4224 and response at line 4323">
                    <trace class="String" >
                        700709000207
                    </trace>
                    <trace class="Integer" >
                        4224
                    </trace>
                    <trace class="Integer" >
                        4323
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:916" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000207 with request at line 4224 and response at line 4323">
                    <trace class="String" >
                        700709000207
                    </trace>
                    <trace class="Integer" >
                        4224
                    </trace>
                    <trace class="Integer" >
                        4323
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:919" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:920" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:921" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:923" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:923" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:925" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:925" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:926" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:926" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:927" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000209 with request at line 4369 and response at line 4468">
                    <trace class="String" >
                        700709000209
                    </trace>
                    <trace class="Integer" >
                        4369
                    </trace>
                    <trace class="Integer" >
                        4468
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:930" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000209 with request at line 4369 and response at line 4468">
                    <trace class="String" >
                        700709000209
                    </trace>
                    <trace class="Integer" >
                        4369
                    </trace>
                    <trace class="Integer" >
                        4468
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:932" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:933" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:935" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:936" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:937" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:938" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:939" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:939" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:940" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:940" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000211 with request at line 4514 and response at line 4613">
                    <trace class="String" >
                        700709000211
                    </trace>
                    <trace class="Integer" >
                        4514
                    </trace>
                    <trace class="Integer" >
                        4613
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:943" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000211 with request at line 4514 and response at line 4613">
                    <trace class="String" >
                        700709000211
                    </trace>
                    <trace class="Integer" >
                        4514
                    </trace>
                    <trace class="Integer" >
                        4613
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:946" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:947" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:949" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:950" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:951" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:953" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:954" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:955" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:955" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:956" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000211 with request at line 4659 and response at line 4709">
                    <trace class="String" >
                        700709000211
                    </trace>
                    <trace class="Integer" >
                        4659
                    </trace>
                    <trace class="Integer" >
                        4709
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:959" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000211 with request at line 4659 and response at line 4709">
                    <trace class="String" >
                        700709000211
                    </trace>
                    <trace class="Integer" >
                        4659
                    </trace>
                    <trace class="Integer" >
                        4709
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:961" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000211 with request at line 4659 and response at line 4709">
                    <trace class="String" >
                        700709000211
                    </trace>
                    <trace class="Integer" >
                        4659
                    </trace>
                    <trace class="Integer" >
                        4709
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:964" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:965" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:967" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:968" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:968" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:970" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:971" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:971" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:972" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:972" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000213 with request at line 4749 and response at line 4848">
                    <trace class="String" >
                        700709000213
                    </trace>
                    <trace class="Integer" >
                        4749
                    </trace>
                    <trace class="Integer" >
                        4848
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:975" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000213 with request at line 4749 and response at line 4848">
                    <trace class="String" >
                        700709000213
                    </trace>
                    <trace class="Integer" >
                        4749
                    </trace>
                    <trace class="Integer" >
                        4848
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:978" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:979" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:980" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:981" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:982" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:983" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:45:984" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:45:985" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:45:985" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:45:986" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000213 with request at line 4894 and response at line 4944">
                    <trace class="String" >
                        700709000213
                    </trace>
                    <trace class="Integer" >
                        4894
                    </trace>
                    <trace class="Integer" >
                        4944
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:989" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000213 with request at line 4894 and response at line 4944">
                    <trace class="String" >
                        700709000213
                    </trace>
                    <trace class="Integer" >
                        4894
                    </trace>
                    <trace class="Integer" >
                        4944
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:45:991" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000213 with request at line 4894 and response at line 4944">
                    <trace class="String" >
                        700709000213
                    </trace>
                    <trace class="Integer" >
                        4894
                    </trace>
                    <trace class="Integer" >
                        4944
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:45:994" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:995" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:996" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:997" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:45:998" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:000" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:001" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:001" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:002" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:002" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000215 with request at line 4984 and response at line 5083">
                    <trace class="String" >
                        700709000215
                    </trace>
                    <trace class="Integer" >
                        4984
                    </trace>
                    <trace class="Integer" >
                        5083
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:005" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000215 with request at line 4984 and response at line 5083">
                    <trace class="String" >
                        700709000215
                    </trace>
                    <trace class="Integer" >
                        4984
                    </trace>
                    <trace class="Integer" >
                        5083
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:008" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:009" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:011" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:013" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:014" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:015" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:016" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:017" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:017" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:018" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000215 with request at line 5129 and response at line 5180">
                    <trace class="String" >
                        700709000215
                    </trace>
                    <trace class="Integer" >
                        5129
                    </trace>
                    <trace class="Integer" >
                        5180
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:021" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000215 with request at line 5129 and response at line 5180">
                    <trace class="String" >
                        700709000215
                    </trace>
                    <trace class="Integer" >
                        5129
                    </trace>
                    <trace class="Integer" >
                        5180
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:025" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000215 with request at line 5129 and response at line 5180">
                    <trace class="String" >
                        700709000215
                    </trace>
                    <trace class="Integer" >
                        5129
                    </trace>
                    <trace class="Integer" >
                        5180
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:028" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:029" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:030" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:031" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:032" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:034" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:036" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="19"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:037" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:037" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:038" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000217 with request at line 5220 and response at line 5319">
                    <trace class="String" >
                        700709000217
                    </trace>
                    <trace class="Integer" >
                        5220
                    </trace>
                    <trace class="Integer" >
                        5319
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:040" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000217 with request at line 5220 and response at line 5319">
                    <trace class="String" >
                        700709000217
                    </trace>
                    <trace class="Integer" >
                        5220
                    </trace>
                    <trace class="Integer" >
                        5319
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:043" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:045" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:046" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:047" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:048" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:049" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:050" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:051" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:052" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:052" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000217 with request at line 5365 and response at line 5415">
                    <trace class="String" >
                        700709000217
                    </trace>
                    <trace class="Integer" >
                        5365
                    </trace>
                    <trace class="Integer" >
                        5415
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:055" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000217 with request at line 5365 and response at line 5415">
                    <trace class="String" >
                        700709000217
                    </trace>
                    <trace class="Integer" >
                        5365
                    </trace>
                    <trace class="Integer" >
                        5415
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:058" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:059" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:061" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:062" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:062" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:064" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:065" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:065" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:066" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:066" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000219 with request at line 5456 and response at line 5555">
                    <trace class="String" >
                        700709000219
                    </trace>
                    <trace class="Integer" >
                        5456
                    </trace>
                    <trace class="Integer" >
                        5555
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:069" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000219 with request at line 5456 and response at line 5555">
                    <trace class="String" >
                        700709000219
                    </trace>
                    <trace class="Integer" >
                        5456
                    </trace>
                    <trace class="Integer" >
                        5555
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:072" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:074" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:075" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:077" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:077" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:079" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:080" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:080" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:081" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:081" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000221 with request at line 5648 and response at line 5747">
                    <trace class="String" >
                        700709000221
                    </trace>
                    <trace class="Integer" >
                        5648
                    </trace>
                    <trace class="Integer" >
                        5747
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:087" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000221 with request at line 5648 and response at line 5747">
                    <trace class="String" >
                        700709000221
                    </trace>
                    <trace class="Integer" >
                        5648
                    </trace>
                    <trace class="Integer" >
                        5747
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:089" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:091" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:092" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:093" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:094" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:095" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:096" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:096" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:097" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:097" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000223 with request at line 5840 and response at line 5939">
                    <trace class="String" >
                        700709000223
                    </trace>
                    <trace class="Integer" >
                        5840
                    </trace>
                    <trace class="Integer" >
                        5939
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:100" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000223 with request at line 5840 and response at line 5939">
                    <trace class="String" >
                        700709000223
                    </trace>
                    <trace class="Integer" >
                        5840
                    </trace>
                    <trace class="Integer" >
                        5939
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:102" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:104" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:105" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:106" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:107" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:109" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:110" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:110" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:111" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:111" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000223 with request at line 5985 and response at line 6031">
                    <trace class="String" >
                        700709000223
                    </trace>
                    <trace class="Integer" >
                        5985
                    </trace>
                    <trace class="Integer" >
                        6031
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:114" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000223 with request at line 5985 and response at line 6031">
                    <trace class="String" >
                        700709000223
                    </trace>
                    <trace class="Integer" >
                        5985
                    </trace>
                    <trace class="Integer" >
                        6031
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:116" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000223 with request at line 5985 and response at line 6031">
                    <trace class="String" >
                        700709000223
                    </trace>
                    <trace class="Integer" >
                        5985
                    </trace>
                    <trace class="Integer" >
                        6031
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:119" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:120" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:122" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:123" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:123" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:125" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:125" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:126" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:127" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:127" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000225 with request at line 6071 and response at line 6170">
                    <trace class="String" >
                        700709000225
                    </trace>
                    <trace class="Integer" >
                        6071
                    </trace>
                    <trace class="Integer" >
                        6170
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:130" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000225 with request at line 6071 and response at line 6170">
                    <trace class="String" >
                        700709000225
                    </trace>
                    <trace class="Integer" >
                        6071
                    </trace>
                    <trace class="Integer" >
                        6170
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:132" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:134" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:135" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:136" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:137" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:138" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:139" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:140" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:140" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:141" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000225 with request at line 6216 and response at line 6262">
                    <trace class="String" >
                        700709000225
                    </trace>
                    <trace class="Integer" >
                        6216
                    </trace>
                    <trace class="Integer" >
                        6262
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:143" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000225 with request at line 6216 and response at line 6262">
                    <trace class="String" >
                        700709000225
                    </trace>
                    <trace class="Integer" >
                        6216
                    </trace>
                    <trace class="Integer" >
                        6262
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:145" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000225 with request at line 6216 and response at line 6262">
                    <trace class="String" >
                        700709000225
                    </trace>
                    <trace class="Integer" >
                        6216
                    </trace>
                    <trace class="Integer" >
                        6262
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:148" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:149" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:151" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:152" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:153" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:154" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:155" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:155" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:156" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:156" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000227 with request at line 6302 and response at line 6401">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6302
                    </trace>
                    <trace class="Integer" >
                        6401
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:160" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000227 with request at line 6302 and response at line 6401">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6302
                    </trace>
                    <trace class="Integer" >
                        6401
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:164" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:165" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:166" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:167" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:168" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:169" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:170" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:170" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:171" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:171" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700709000227 with request at line 6447 and response at line 6492">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6447
                    </trace>
                    <trace class="Integer" >
                        6492
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:174" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700709000227 with request at line 6447 and response at line 6492">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6447
                    </trace>
                    <trace class="Integer" >
                        6492
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:177" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700709000227 with request at line 6447 and response at line 6492">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6447
                    </trace>
                    <trace class="Integer" >
                        6492
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:179" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000227 with request at line 6447 and response at line 6492">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6447
                    </trace>
                    <trace class="Integer" >
                        6492
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:182" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000227 with request at line 6447 and response at line 6492">
                    <trace class="String" >
                        700709000227
                    </trace>
                    <trace class="Integer" >
                        6447
                    </trace>
                    <trace class="Integer" >
                        6492
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:185" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:186" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:188" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:189" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:189" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:191" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:192" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="22"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:192" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:193" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:193" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700709000229 with request at line 6530 and response at line 6629">
                    <trace class="String" >
                        700709000229
                    </trace>
                    <trace class="Integer" >
                        6530
                    </trace>
                    <trace class="Integer" >
                        6629
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:195" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700709000229 with request at line 6530 and response at line 6629">
                    <trace class="String" >
                        700709000229
                    </trace>
                    <trace class="Integer" >
                        6530
                    </trace>
                    <trace class="Integer" >
                        6629
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:198" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000229 with request at line 6530 and response at line 6629">
                    <trace class="String" >
                        700709000229
                    </trace>
                    <trace class="Integer" >
                        6530
                    </trace>
                    <trace class="Integer" >
                        6629
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:201" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:203" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:204" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:205" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:206" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:207" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:208" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:209" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:209" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:210" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000231 with request at line 6676 and response at line 6775">
                    <trace class="String" >
                        700709000231
                    </trace>
                    <trace class="Integer" >
                        6676
                    </trace>
                    <trace class="Integer" >
                        6775
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:213" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000231 with request at line 6676 and response at line 6775">
                    <trace class="String" >
                        700709000231
                    </trace>
                    <trace class="Integer" >
                        6676
                    </trace>
                    <trace class="Integer" >
                        6775
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:215" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:216" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:218" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:219" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:220" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:221" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:222" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:223" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:223" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:224" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000231 with request at line 6821 and response at line 6867">
                    <trace class="String" >
                        700709000231
                    </trace>
                    <trace class="Integer" >
                        6821
                    </trace>
                    <trace class="Integer" >
                        6867
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:226" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000231 with request at line 6821 and response at line 6867">
                    <trace class="String" >
                        700709000231
                    </trace>
                    <trace class="Integer" >
                        6821
                    </trace>
                    <trace class="Integer" >
                        6867
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:229" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000231 with request at line 6821 and response at line 6867">
                    <trace class="String" >
                        700709000231
                    </trace>
                    <trace class="Integer" >
                        6821
                    </trace>
                    <trace class="Integer" >
                        6867
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:231" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:233" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:235" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:236" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:237" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:239" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:239" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:240" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:240" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:241" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000233 with request at line 6907 and response at line 7006">
                    <trace class="String" >
                        700709000233
                    </trace>
                    <trace class="Integer" >
                        6907
                    </trace>
                    <trace class="Integer" >
                        7006
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:244" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000233 with request at line 6907 and response at line 7006">
                    <trace class="String" >
                        700709000233
                    </trace>
                    <trace class="Integer" >
                        6907
                    </trace>
                    <trace class="Integer" >
                        7006
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:247" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:248" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:249" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:250" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:251" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:253" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:254" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:255" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:255" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:256" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700709000233 with request at line 7052 and response at line 7098">
                    <trace class="String" >
                        700709000233
                    </trace>
                    <trace class="Integer" >
                        7052
                    </trace>
                    <trace class="Integer" >
                        7098
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:259" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700709000233 with request at line 7052 and response at line 7098">
                    <trace class="String" >
                        700709000233
                    </trace>
                    <trace class="Integer" >
                        7052
                    </trace>
                    <trace class="Integer" >
                        7098
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:262" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700709000233 with request at line 7052 and response at line 7098">
                    <trace class="String" >
                        700709000233
                    </trace>
                    <trace class="Integer" >
                        7052
                    </trace>
                    <trace class="Integer" >
                        7098
                    </trace>
                </warning>
                <elapsed_time milliseconds="11"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:266" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:267" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:269" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:270" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:271" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:272" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:273" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="19"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:273" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:274" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:275" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:276" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:277" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:278" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:279" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:280" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:281" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:282" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:282" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:283" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:284" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:286" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:287" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:288" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:290" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:290" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:291" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:292" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:292" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:294" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:295" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:296" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:297" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:298" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:299" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:299" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:300" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:301" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:302" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:303" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:304" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:304" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:306" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:307" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:307" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:308" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:308" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:309" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:311" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:312" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:312" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:314" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:315" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:315" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:316" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:316" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:317" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:319" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:320" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:321" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:322" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:323" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:324" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:325" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:326" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:327" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:328" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:329" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:330" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:331" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:332" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:333" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:333" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:334" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:335" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:337" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:338" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:338" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:340" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:341" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:341" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:342" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:343" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:344" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:345" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:346" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:347" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:348" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:349" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:350" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:350" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:351" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:352" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:354" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:355" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:355" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:357" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:358" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:358" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:359" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:359" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000255 with request at line 8619 and response at line 8718">
                    <trace class="String" >
                        700710000255
                    </trace>
                    <trace class="Integer" >
                        8619
                    </trace>
                    <trace class="Integer" >
                        8718
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:362" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000255 with request at line 8619 and response at line 8718">
                    <trace class="String" >
                        700710000255
                    </trace>
                    <trace class="Integer" >
                        8619
                    </trace>
                    <trace class="Integer" >
                        8718
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:366" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:367" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:369" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:370" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:371" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:372" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:373" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:376" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:377" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:377" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000257 with request at line 8764 and response at line 8863">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8764
                    </trace>
                    <trace class="Integer" >
                        8863
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:380" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000257 with request at line 8764 and response at line 8863">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8764
                    </trace>
                    <trace class="Integer" >
                        8863
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:383" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:384" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:385" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:386" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:387" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:388" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:389" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:389" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:390" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:390" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700710000257 with request at line 8909 and response at line 8955">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8909
                    </trace>
                    <trace class="Integer" >
                        8955
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:393" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700710000257 with request at line 8909 and response at line 8955">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8909
                    </trace>
                    <trace class="Integer" >
                        8955
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:395" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700710000257 with request at line 8909 and response at line 8955">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8909
                    </trace>
                    <trace class="Integer" >
                        8955
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:397" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000257 with request at line 8909 and response at line 8955">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8909
                    </trace>
                    <trace class="Integer" >
                        8955
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:400" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700710000257 with request at line 8909 and response at line 8955">
                    <trace class="String" >
                        700710000257
                    </trace>
                    <trace class="Integer" >
                        8909
                    </trace>
                    <trace class="Integer" >
                        8955
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:403" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:404" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:405" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:406" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:407" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:408" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:409" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="20"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:410" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:410" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:411" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000259 with request at line 8994 and response at line 9093">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        8994
                    </trace>
                    <trace class="Integer" >
                        9093
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:413" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000259 with request at line 8994 and response at line 9093">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        8994
                    </trace>
                    <trace class="Integer" >
                        9093
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:417" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:419" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:420" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:422" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:422" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:423" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:424" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:425" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:426" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:427" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700710000259 with request at line 9139 and response at line 9185">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        9139
                    </trace>
                    <trace class="Integer" >
                        9185
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:430" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700710000259 with request at line 9139 and response at line 9185">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        9139
                    </trace>
                    <trace class="Integer" >
                        9185
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:433" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700710000259 with request at line 9139 and response at line 9185">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        9139
                    </trace>
                    <trace class="Integer" >
                        9185
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:435" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000259 with request at line 9139 and response at line 9185">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        9139
                    </trace>
                    <trace class="Integer" >
                        9185
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:438" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700710000259 with request at line 9139 and response at line 9185">
                    <trace class="String" >
                        700710000259
                    </trace>
                    <trace class="Integer" >
                        9139
                    </trace>
                    <trace class="Integer" >
                        9185
                    </trace>
                </warning>
                <elapsed_time milliseconds="14"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:440" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:441" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:443" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:445" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:446" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:448" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:448" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="24"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:449" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:450" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:450" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000261 with request at line 9224 and response at line 9323">
                    <trace class="String" >
                        700710000261
                    </trace>
                    <trace class="Integer" >
                        9224
                    </trace>
                    <trace class="Integer" >
                        9323
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:454" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000261 with request at line 9224 and response at line 9323">
                    <trace class="String" >
                        700710000261
                    </trace>
                    <trace class="Integer" >
                        9224
                    </trace>
                    <trace class="Integer" >
                        9323
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:456" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:457" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:459" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:460" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:460" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:462" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:463" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:463" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:464" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:465" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000263 with request at line 9369 and response at line 9468">
                    <trace class="String" >
                        700710000263
                    </trace>
                    <trace class="Integer" >
                        9369
                    </trace>
                    <trace class="Integer" >
                        9468
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:467" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000263 with request at line 9369 and response at line 9468">
                    <trace class="String" >
                        700710000263
                    </trace>
                    <trace class="Integer" >
                        9369
                    </trace>
                    <trace class="Integer" >
                        9468
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:470" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:472" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:473" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:474" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:475" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:477" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:478" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:478" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:479" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:479" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700710000265 with request at line 9514 and response at line 9613">
                    <trace class="String" >
                        700710000265
                    </trace>
                    <trace class="Integer" >
                        9514
                    </trace>
                    <trace class="Integer" >
                        9613
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:482" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700710000265 with request at line 9514 and response at line 9613">
                    <trace class="String" >
                        700710000265
                    </trace>
                    <trace class="Integer" >
                        9514
                    </trace>
                    <trace class="Integer" >
                        9613
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:485" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:486" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:488" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:489" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:489" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:491" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:491" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:492" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:492" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:493" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700711000267 with request at line 9659 and response at line 9758">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9659
                    </trace>
                    <trace class="Integer" >
                        9758
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:495" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700711000267 with request at line 9659 and response at line 9758">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9659
                    </trace>
                    <trace class="Integer" >
                        9758
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:498" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:499" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:500" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:501" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:502" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:503" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:503" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:504" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:505" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:505" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700711000267 with request at line 9804 and response at line 9850">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9804
                    </trace>
                    <trace class="Integer" >
                        9850
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:507" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700711000267 with request at line 9804 and response at line 9850">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9804
                    </trace>
                    <trace class="Integer" >
                        9850
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:510" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700711000267 with request at line 9804 and response at line 9850">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9804
                    </trace>
                    <trace class="Integer" >
                        9850
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:512" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700711000267 with request at line 9804 and response at line 9850">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9804
                    </trace>
                    <trace class="Integer" >
                        9850
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:514" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700711000267 with request at line 9804 and response at line 9850">
                    <trace class="String" >
                        700711000267
                    </trace>
                    <trace class="Integer" >
                        9804
                    </trace>
                    <trace class="Integer" >
                        9850
                    </trace>
                </warning>
                <elapsed_time milliseconds="11"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:516" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:517" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:518" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:519" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:520" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:521" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:522" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:522" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:523" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:523" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700711000269 with request at line 9889 and response at line 9988">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        9889
                    </trace>
                    <trace class="Integer" >
                        9988
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:526" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700711000269 with request at line 9889 and response at line 9988">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        9889
                    </trace>
                    <trace class="Integer" >
                        9988
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:528" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:529" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:531" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:532" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:532" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:534" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:535" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:536" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:537" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:537" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700711000269 with request at line 10034 and response at line 10080">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        10034
                    </trace>
                    <trace class="Integer" >
                        10080
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:539" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700711000269 with request at line 10034 and response at line 10080">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        10034
                    </trace>
                    <trace class="Integer" >
                        10080
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:542" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700711000269 with request at line 10034 and response at line 10080">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        10034
                    </trace>
                    <trace class="Integer" >
                        10080
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:544" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700711000269 with request at line 10034 and response at line 10080">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        10034
                    </trace>
                    <trace class="Integer" >
                        10080
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:547" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700711000269 with request at line 10034 and response at line 10080">
                    <trace class="String" >
                        700711000269
                    </trace>
                    <trace class="Integer" >
                        10034
                    </trace>
                    <trace class="Integer" >
                        10080
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:549" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:550" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:552" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:553" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:553" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:555" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:555" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="20"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:556" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:557" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:557" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:558" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:560" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:561" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:561" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:563" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:565" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:566" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:566" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:567" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:569" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:570" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:571" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:571" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:573" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:574" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:574" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:575" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:575" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:577" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:579" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:580" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:580" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:582" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:583" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:584" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:584" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:585" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:587" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:588" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:589" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:590" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="7"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:598" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:599" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:599" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:600" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:600" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:602" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:604" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:605" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:605" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:607" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:608" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:609" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:609" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:610" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:612" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:613" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:614" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:615" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:617" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:618" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:619" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:620" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:621" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:622" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:623" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:624" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:625" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:626" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:627" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:627" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:628" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:629" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:630" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:631" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:632" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:633" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:634" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:635" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:636" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:636" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:637" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:638" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:639" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:640" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:641" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:642" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:643" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:643" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:644" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:644" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:645" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:647" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:648" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:648" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:649" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:650" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:651" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:651" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:652" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:653" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:654" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:655" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:656" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:657" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:658" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:659" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:659" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:660" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:661" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:662" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:663" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:664" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:665" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:667" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:667" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:668" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:669" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:671" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:672" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:673" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:674" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:675" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:676" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:676" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:677" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:677" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:679" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:680" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:681" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:682" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:683" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:684" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:684" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:685" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:685" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:687" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:688" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:689" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:689" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:691" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:691" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:692" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:692" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:693" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:694" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:696" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:696" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:697" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:698" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:699" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:700" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:700" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:701" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:702" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:703" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:704" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:705" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:706" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:707" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:707" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:708" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:709" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:710" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:711" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:712" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:712" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:714" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:715" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:715" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:716" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:716" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:718" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:720" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:722" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:722" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:724" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:725" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="10"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:725" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:726" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:726" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:727" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:729" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:730" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:730" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:732" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:733" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:733" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:734" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:734" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:736" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:737" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:738" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:738" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:740" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:741" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:741" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:742" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:742" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:743" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:745" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:746" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:746" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:748" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:748" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:749" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:749" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:750" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:751" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:752" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:753" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:754" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:755" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:755" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:756" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:756" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:757" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:758" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:759" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:760" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:761" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:762" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:762" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:763" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:763" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:764" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700815000317 with request at line 13671 and response at line 13729">
                    <trace class="String" >
                        700815000317
                    </trace>
                    <trace class="Integer" >
                        13671
                    </trace>
                    <trace class="Integer" >
                        13729
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:767" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700815000317 with request at line 13671 and response at line 13729">
                    <trace class="String" >
                        700815000317
                    </trace>
                    <trace class="Integer" >
                        13671
                    </trace>
                    <trace class="Integer" >
                        13729
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:769" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700815000317 with request at line 13671 and response at line 13729">
                    <trace class="String" >
                        700815000317
                    </trace>
                    <trace class="Integer" >
                        13671
                    </trace>
                    <trace class="Integer" >
                        13729
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:771" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:772" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:773" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:774" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:775" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:776" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:777" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:778" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:778" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:779" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:780" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:781" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:782" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:783" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:784" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:785" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:786" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:786" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:787" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:788" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:789" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:790" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:790" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:791" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:792" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:793" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:793" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:794" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:795" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:796" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:797" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:797" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:799" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:799" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:800" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:800" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:801" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000325 with request at line 14153 and response at line 14219">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        14153
                    </trace>
                    <trace class="Integer" >
                        14219
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:803" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000325 with request at line 14153 and response at line 14219">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        14153
                    </trace>
                    <trace class="Integer" >
                        14219
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:806" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:807" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:808" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:809" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:810" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:811" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:811" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:812" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:812" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:813" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000327 with request at line 14264 and response at line 14330">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14264
                    </trace>
                    <trace class="Integer" >
                        14330
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:815" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000327 with request at line 14264 and response at line 14330">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14264
                    </trace>
                    <trace class="Integer" >
                        14330
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:817" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:818" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:820" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:820" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:821" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:822" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:823" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:823" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:824" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:824" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700816000327 with request at line 14375 and response at line 14421">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14375
                    </trace>
                    <trace class="Integer" >
                        14421
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:826" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700816000327 with request at line 14375 and response at line 14421">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14375
                    </trace>
                    <trace class="Integer" >
                        14421
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:828" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700816000327 with request at line 14375 and response at line 14421">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14375
                    </trace>
                    <trace class="Integer" >
                        14421
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:830" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000327 with request at line 14375 and response at line 14421">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14375
                    </trace>
                    <trace class="Integer" >
                        14421
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:833" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700816000327 with request at line 14375 and response at line 14421">
                    <trace class="String" >
                        700816000327
                    </trace>
                    <trace class="Integer" >
                        14375
                    </trace>
                    <trace class="Integer" >
                        14421
                    </trace>
                </warning>
                <elapsed_time milliseconds="11"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:835" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:837" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:838" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:839" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:840" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:841" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:841" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="19"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:842" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:842" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:843" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000329 with request at line 14460 and response at line 14526">
                    <trace class="String" >
                        700816000329
                    </trace>
                    <trace class="Integer" >
                        14460
                    </trace>
                    <trace class="Integer" >
                        14526
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:845" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000329 with request at line 14460 and response at line 14526">
                    <trace class="String" >
                        700816000329
                    </trace>
                    <trace class="Integer" >
                        14460
                    </trace>
                    <trace class="Integer" >
                        14526
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:847" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:848" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:849" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:850" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:851" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:852" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:853" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:853" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:854" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:854" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000329 with request at line 14571 and response at line 14617">
                    <trace class="String" >
                        700816000329
                    </trace>
                    <trace class="Integer" >
                        14571
                    </trace>
                    <trace class="Integer" >
                        14617
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:856" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000329 with request at line 14571 and response at line 14617">
                    <trace class="String" >
                        700816000329
                    </trace>
                    <trace class="Integer" >
                        14571
                    </trace>
                    <trace class="Integer" >
                        14617
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:858" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700816000329 with request at line 14571 and response at line 14617">
                    <trace class="String" >
                        700816000329
                    </trace>
                    <trace class="Integer" >
                        14571
                    </trace>
                    <trace class="Integer" >
                        14617
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:861" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:861" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:863" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:864" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:864" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:865" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:867" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:868" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:868" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:869" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000335 with request at line 14658 and response at line 14724">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14658
                    </trace>
                    <trace class="Integer" >
                        14724
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:871" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000335 with request at line 14658 and response at line 14724">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14658
                    </trace>
                    <trace class="Integer" >
                        14724
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:873" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:874" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:875" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:876" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:877" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:878" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:878" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:879" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:879" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:880" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700816000335 with request at line 14769 and response at line 14816">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14769
                    </trace>
                    <trace class="Integer" >
                        14816
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:882" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700816000335 with request at line 14769 and response at line 14816">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14769
                    </trace>
                    <trace class="Integer" >
                        14816
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:885" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700816000335 with request at line 14769 and response at line 14816">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14769
                    </trace>
                    <trace class="Integer" >
                        14816
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:887" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000335 with request at line 14769 and response at line 14816">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14769
                    </trace>
                    <trace class="Integer" >
                        14816
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:889" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700816000335 with request at line 14769 and response at line 14816">
                    <trace class="String" >
                        700816000335
                    </trace>
                    <trace class="Integer" >
                        14769
                    </trace>
                    <trace class="Integer" >
                        14816
                    </trace>
                </warning>
                <elapsed_time milliseconds="13"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:892" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:893" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:894" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:895" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:896" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:897" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:898" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="19"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:898" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:899" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:899" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000337 with request at line 14855 and response at line 14921">
                    <trace class="String" >
                        700816000337
                    </trace>
                    <trace class="Integer" >
                        14855
                    </trace>
                    <trace class="Integer" >
                        14921
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:901" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000337 with request at line 14855 and response at line 14921">
                    <trace class="String" >
                        700816000337
                    </trace>
                    <trace class="Integer" >
                        14855
                    </trace>
                    <trace class="Integer" >
                        14921
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:903" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="14"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:918" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:919" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:920" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:920" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:921" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:922" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="25"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:923" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:923" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:924" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000337 with request at line 14966 and response at line 15013">
                    <trace class="String" >
                        700816000337
                    </trace>
                    <trace class="Integer" >
                        14966
                    </trace>
                    <trace class="Integer" >
                        15013
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:926" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000337 with request at line 14966 and response at line 15013">
                    <trace class="String" >
                        700816000337
                    </trace>
                    <trace class="Integer" >
                        14966
                    </trace>
                    <trace class="Integer" >
                        15013
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:929" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700816000337 with request at line 14966 and response at line 15013">
                    <trace class="String" >
                        700816000337
                    </trace>
                    <trace class="Integer" >
                        14966
                    </trace>
                    <trace class="Integer" >
                        15013
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:931" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:932" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:934" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:935" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:935" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:936" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:937" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:938" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:938" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:939" line="130" exception_class="T_visa_recon_generator" message="Card number F002 is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:941" line="130" exception_class="T_visa_recon_generator" message="F004 is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:943" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:945" line="130" exception_class="T_visa_recon_generator" message="F049 is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:948" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:950" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN  with request at line 15054 and response at line 15101">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15054
                    </trace>
                    <trace class="Integer" >
                        15101
                    </trace>
                </warning>
                <elapsed_time milliseconds="15"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:953" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:954" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:956" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:957" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:957" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:958" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:959" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="21"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:959" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:960" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:961" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000339 with request at line 15139 and response at line 15205">
                    <trace class="String" >
                        700816000339
                    </trace>
                    <trace class="Integer" >
                        15139
                    </trace>
                    <trace class="Integer" >
                        15205
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:963" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000339 with request at line 15139 and response at line 15205">
                    <trace class="String" >
                        700816000339
                    </trace>
                    <trace class="Integer" >
                        15139
                    </trace>
                    <trace class="Integer" >
                        15205
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:965" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:966" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:968" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:970" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:970" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:972" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:973" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:973" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:974" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:974" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700816000339 with request at line 15250 and response at line 15297">
                    <trace class="String" >
                        700816000339
                    </trace>
                    <trace class="Integer" >
                        15250
                    </trace>
                    <trace class="Integer" >
                        15297
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:976" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000339 with request at line 15250 and response at line 15297">
                    <trace class="String" >
                        700816000339
                    </trace>
                    <trace class="Integer" >
                        15250
                    </trace>
                    <trace class="Integer" >
                        15297
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:979" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700816000339 with request at line 15250 and response at line 15297">
                    <trace class="String" >
                        700816000339
                    </trace>
                    <trace class="Integer" >
                        15250
                    </trace>
                    <trace class="Integer" >
                        15297
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:981" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:983" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:984" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:985" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:985" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:987" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:987" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:46:988" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:46:988" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:46:989" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000341 with request at line 15338 and response at line 15404">
                    <trace class="String" >
                        700817000341
                    </trace>
                    <trace class="Integer" >
                        15338
                    </trace>
                    <trace class="Integer" >
                        15404
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:46:991" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000341 with request at line 15338 and response at line 15404">
                    <trace class="String" >
                        700817000341
                    </trace>
                    <trace class="Integer" >
                        15338
                    </trace>
                    <trace class="Integer" >
                        15404
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:46:994" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:995" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:996" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:997" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:998" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:46:999" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:46:999" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:000" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:000" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:001" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000342 with request at line 15449 and response at line 15496">
                    <trace class="String" >
                        700817000342
                    </trace>
                    <trace class="Integer" >
                        15449
                    </trace>
                    <trace class="Integer" >
                        15496
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:003" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000342 with request at line 15449 and response at line 15496">
                    <trace class="String" >
                        700817000342
                    </trace>
                    <trace class="Integer" >
                        15449
                    </trace>
                    <trace class="Integer" >
                        15496
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:006" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700817000342 with request at line 15449 and response at line 15496">
                    <trace class="String" >
                        700817000342
                    </trace>
                    <trace class="Integer" >
                        15449
                    </trace>
                    <trace class="Integer" >
                        15496
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:008" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:009" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:010" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:011" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:012" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:013" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:013" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:014" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:014" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:015" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000343 with request at line 15537 and response at line 15603">
                    <trace class="String" >
                        700817000343
                    </trace>
                    <trace class="Integer" >
                        15537
                    </trace>
                    <trace class="Integer" >
                        15603
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:017" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000343 with request at line 15537 and response at line 15603">
                    <trace class="String" >
                        700817000343
                    </trace>
                    <trace class="Integer" >
                        15537
                    </trace>
                    <trace class="Integer" >
                        15603
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:020" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:021" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:022" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:023" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:024" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:025" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:026" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:026" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:027" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:027" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000344 with request at line 15648 and response at line 15695">
                    <trace class="String" >
                        700817000344
                    </trace>
                    <trace class="Integer" >
                        15648
                    </trace>
                    <trace class="Integer" >
                        15695
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:030" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000344 with request at line 15648 and response at line 15695">
                    <trace class="String" >
                        700817000344
                    </trace>
                    <trace class="Integer" >
                        15648
                    </trace>
                    <trace class="Integer" >
                        15695
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:033" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700817000344 with request at line 15648 and response at line 15695">
                    <trace class="String" >
                        700817000344
                    </trace>
                    <trace class="Integer" >
                        15648
                    </trace>
                    <trace class="Integer" >
                        15695
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:035" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:036" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:037" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:038" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:038" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:040" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:040" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:041" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:042" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:042" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000345 with request at line 15736 and response at line 15802">
                    <trace class="String" >
                        700817000345
                    </trace>
                    <trace class="Integer" >
                        15736
                    </trace>
                    <trace class="Integer" >
                        15802
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:045" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000345 with request at line 15736 and response at line 15802">
                    <trace class="String" >
                        700817000345
                    </trace>
                    <trace class="Integer" >
                        15736
                    </trace>
                    <trace class="Integer" >
                        15802
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:047" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:048" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:049" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:050" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:050" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:051" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:052" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:053" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:053" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:054" line="130" exception_class="T_visa_recon_generator" message="Card number F002 is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:056" line="130" exception_class="T_visa_recon_generator" message="F004 is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:058" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:060" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:062" line="130" exception_class="T_visa_recon_generator" message="F049 is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:064" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:067" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:070" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN  with request at line 15847 and response at line 15894">
                    <trace class="String" >
                        
                    </trace>
                    <trace class="Integer" >
                        15847
                    </trace>
                    <trace class="Integer" >
                        15894
                    </trace>
                </warning>
                <elapsed_time milliseconds="19"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:072" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:073" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:074" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:075" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:075" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:077" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:077" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="25"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:078" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:078" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:079" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:080" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:081" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:082" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:083" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:085" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:086" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:086" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:086" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:087" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:088" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:089" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:090" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:091" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:092" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:092" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:093" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:094" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:094" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:095" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:097" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:097" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:098" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:099" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:100" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:100" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:101" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:101" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:102" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:104" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:105" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:105" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:106" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:107" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:107" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:108" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:109" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:110" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:111" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:112" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:112" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:114" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:115" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:116" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:116" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:117" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:118" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:119" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:120" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:120" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:121" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:122" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:123" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:123" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:124" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:125" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:126" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:127" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:127" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:128" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:129" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:130" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:130" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:131" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:132" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:133" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:134" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:135" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:136" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:137" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:138" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:138" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:139" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:140" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:141" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:142" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:142" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:144" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:144" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:145" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:146" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:146" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:147" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:149" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:150" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:150" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:151" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:152" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:153" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:153" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:154" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700817000347 with request at line 17169 and response at line 17221">
                    <trace class="String" >
                        700817000347
                    </trace>
                    <trace class="Integer" >
                        17169
                    </trace>
                    <trace class="Integer" >
                        17221
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:156" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700817000347 with request at line 17169 and response at line 17221">
                    <trace class="String" >
                        700817000347
                    </trace>
                    <trace class="Integer" >
                        17169
                    </trace>
                    <trace class="Integer" >
                        17221
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:158" line="130" exception_class="T_visa_recon_generator" message="F062 2 Transaction Id is missing for transaction with RRN 700817000347 with request at line 17169 and response at line 17221">
                    <trace class="String" >
                        700817000347
                    </trace>
                    <trace class="Integer" >
                        17169
                    </trace>
                    <trace class="Integer" >
                        17221
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:161" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:162" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:163" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:164" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:164" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:166" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:166" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:167" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:167" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:168" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700816000325 with request at line 17468 and response at line 17518">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        17468
                    </trace>
                    <trace class="Integer" >
                        17518
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:171" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700816000325 with request at line 17468 and response at line 17518">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        17468
                    </trace>
                    <trace class="Integer" >
                        17518
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:173" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700816000325 with request at line 17468 and response at line 17518">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        17468
                    </trace>
                    <trace class="Integer" >
                        17518
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:175" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700816000325 with request at line 17468 and response at line 17518">
                    <trace class="String" >
                        700816000325
                    </trace>
                    <trace class="Integer" >
                        17468
                    </trace>
                    <trace class="Integer" >
                        17518
                    </trace>
                </warning>
                <elapsed_time milliseconds="11"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:178" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:179" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:180" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:181" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:182" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:183" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:184" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:185" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:185" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:186" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700916000367 with request at line 17557 and response at line 17623">
                    <trace class="String" >
                        700916000367
                    </trace>
                    <trace class="Integer" >
                        17557
                    </trace>
                    <trace class="Integer" >
                        17623
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:188" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700916000367 with request at line 17557 and response at line 17623">
                    <trace class="String" >
                        700916000367
                    </trace>
                    <trace class="Integer" >
                        17557
                    </trace>
                    <trace class="Integer" >
                        17623
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:191" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:192" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:194" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:195" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:196" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:197" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:198" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:198" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:199" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:199" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:200" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:202" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:203" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:204" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:205" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:206" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:207" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:207" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:208" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:209" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:210" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:211" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:212" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:213" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:214" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:215" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:215" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:215" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000373 with request at line 17906 and response at line 17972">
                    <trace class="String" >
                        700917000373
                    </trace>
                    <trace class="Integer" >
                        17906
                    </trace>
                    <trace class="Integer" >
                        17972
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:218" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000373 with request at line 17906 and response at line 17972">
                    <trace class="String" >
                        700917000373
                    </trace>
                    <trace class="Integer" >
                        17906
                    </trace>
                    <trace class="Integer" >
                        17972
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:221" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:222" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:224" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:225" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:225" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:226" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:227" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:227" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:228" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:228" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000375 with request at line 18017 and response at line 18107">
                    <trace class="String" >
                        700917000375
                    </trace>
                    <trace class="Integer" >
                        18017
                    </trace>
                    <trace class="Integer" >
                        18107
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:231" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000375 with request at line 18017 and response at line 18107">
                    <trace class="String" >
                        700917000375
                    </trace>
                    <trace class="Integer" >
                        18017
                    </trace>
                    <trace class="Integer" >
                        18107
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:234" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:235" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:236" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:237" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:238" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:239" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:239" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:241" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:241" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:242" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000377 with request at line 18153 and response at line 18243">
                    <trace class="String" >
                        700917000377
                    </trace>
                    <trace class="Integer" >
                        18153
                    </trace>
                    <trace class="Integer" >
                        18243
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:244" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000377 with request at line 18153 and response at line 18243">
                    <trace class="String" >
                        700917000377
                    </trace>
                    <trace class="Integer" >
                        18153
                    </trace>
                    <trace class="Integer" >
                        18243
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:246" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:247" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:248" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:249" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:250" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:251" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:252" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:252" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:253" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:254" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000379 with request at line 18289 and response at line 18379">
                    <trace class="String" >
                        700917000379
                    </trace>
                    <trace class="Integer" >
                        18289
                    </trace>
                    <trace class="Integer" >
                        18379
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:256" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000379 with request at line 18289 and response at line 18379">
                    <trace class="String" >
                        700917000379
                    </trace>
                    <trace class="Integer" >
                        18289
                    </trace>
                    <trace class="Integer" >
                        18379
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:258" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:259" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:260" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:261" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:261" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:263" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:263" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:264" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:264" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:265" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000381 with request at line 18425 and response at line 18515">
                    <trace class="String" >
                        700917000381
                    </trace>
                    <trace class="Integer" >
                        18425
                    </trace>
                    <trace class="Integer" >
                        18515
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:267" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000381 with request at line 18425 and response at line 18515">
                    <trace class="String" >
                        700917000381
                    </trace>
                    <trace class="Integer" >
                        18425
                    </trace>
                    <trace class="Integer" >
                        18515
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:270" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:272" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:273" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:274" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:274" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:275" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:276" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:277" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:277" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:278" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000383 with request at line 18561 and response at line 18651">
                    <trace class="String" >
                        700917000383
                    </trace>
                    <trace class="Integer" >
                        18561
                    </trace>
                    <trace class="Integer" >
                        18651
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:280" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000383 with request at line 18561 and response at line 18651">
                    <trace class="String" >
                        700917000383
                    </trace>
                    <trace class="Integer" >
                        18561
                    </trace>
                    <trace class="Integer" >
                        18651
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:283" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:284" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:286" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:287" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:287" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:288" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:289" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:290" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:290" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:291" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000385 with request at line 18786 and response at line 18876">
                    <trace class="String" >
                        700917000385
                    </trace>
                    <trace class="Integer" >
                        18786
                    </trace>
                    <trace class="Integer" >
                        18876
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:293" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000385 with request at line 18786 and response at line 18876">
                    <trace class="String" >
                        700917000385
                    </trace>
                    <trace class="Integer" >
                        18786
                    </trace>
                    <trace class="Integer" >
                        18876
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:296" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:297" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:298" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:299" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:299" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:300" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:301" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:302" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:303" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:303" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700917000387 with request at line 18922 and response at line 19012">
                    <trace class="String" >
                        700917000387
                    </trace>
                    <trace class="Integer" >
                        18922
                    </trace>
                    <trace class="Integer" >
                        19012
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:305" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700917000387 with request at line 18922 and response at line 19012">
                    <trace class="String" >
                        700917000387
                    </trace>
                    <trace class="Integer" >
                        18922
                    </trace>
                    <trace class="Integer" >
                        19012
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:307" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:308" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:309" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:310" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:311" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:312" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:313" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:313" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:313" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:314" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000389 with request at line 19058 and response at line 19148">
                    <trace class="String" >
                        700918000389
                    </trace>
                    <trace class="Integer" >
                        19058
                    </trace>
                    <trace class="Integer" >
                        19148
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:316" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000389 with request at line 19058 and response at line 19148">
                    <trace class="String" >
                        700918000389
                    </trace>
                    <trace class="Integer" >
                        19058
                    </trace>
                    <trace class="Integer" >
                        19148
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:320" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:320" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:321" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:322" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:322" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:324" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:326" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:326" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:327" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:327" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000391 with request at line 19241 and response at line 19331">
                    <trace class="String" >
                        700918000391
                    </trace>
                    <trace class="Integer" >
                        19241
                    </trace>
                    <trace class="Integer" >
                        19331
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:329" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000391 with request at line 19241 and response at line 19331">
                    <trace class="String" >
                        700918000391
                    </trace>
                    <trace class="Integer" >
                        19241
                    </trace>
                    <trace class="Integer" >
                        19331
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:332" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:333" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:335" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:336" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:336" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:337" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:338" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:339" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:339" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:340" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000393 with request at line 19424 and response at line 19514">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19424
                    </trace>
                    <trace class="Integer" >
                        19514
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:342" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000393 with request at line 19424 and response at line 19514">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19424
                    </trace>
                    <trace class="Integer" >
                        19514
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:344" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:345" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:347" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:348" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:349" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:350" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:351" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:351" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:352" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:352" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700918000393 with request at line 19560 and response at line 19610">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19560
                    </trace>
                    <trace class="Integer" >
                        19610
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:355" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700918000393 with request at line 19560 and response at line 19610">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19560
                    </trace>
                    <trace class="Integer" >
                        19610
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:357" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700918000393 with request at line 19560 and response at line 19610">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19560
                    </trace>
                    <trace class="Integer" >
                        19610
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:360" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000393 with request at line 19560 and response at line 19610">
                    <trace class="String" >
                        700918000393
                    </trace>
                    <trace class="Integer" >
                        19560
                    </trace>
                    <trace class="Integer" >
                        19610
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:362" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:363" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:365" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:365" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:366" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:367" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:368" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="18"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:369" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:369" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:370" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000395 with request at line 19649 and response at line 19739">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19649
                    </trace>
                    <trace class="Integer" >
                        19739
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:373" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000395 with request at line 19649 and response at line 19739">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19649
                    </trace>
                    <trace class="Integer" >
                        19739
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:375" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:376" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:377" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:378" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:379" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:380" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:380" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:381" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:381" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:382" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700918000395 with request at line 19785 and response at line 19835">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19785
                    </trace>
                    <trace class="Integer" >
                        19835
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:384" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700918000395 with request at line 19785 and response at line 19835">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19785
                    </trace>
                    <trace class="Integer" >
                        19835
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:387" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700918000395 with request at line 19785 and response at line 19835">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19785
                    </trace>
                    <trace class="Integer" >
                        19835
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:389" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000395 with request at line 19785 and response at line 19835">
                    <trace class="String" >
                        700918000395
                    </trace>
                    <trace class="Integer" >
                        19785
                    </trace>
                    <trace class="Integer" >
                        19835
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:392" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:393" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:394" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:395" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:395" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:397" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:398" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="3"/>
            </PostfixExpression>
            <elapsed_time milliseconds="20"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:401" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:401" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:403" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000397 with request at line 19874 and response at line 19964">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        19874
                    </trace>
                    <trace class="Integer" >
                        19964
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:405" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000397 with request at line 19874 and response at line 19964">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        19874
                    </trace>
                    <trace class="Integer" >
                        19964
                    </trace>
                </warning>
                <elapsed_time milliseconds="7"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:408" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:409" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:410" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:411" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:412" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:413" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:415" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:415" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:416" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:416" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700918000397 with request at line 20010 and response at line 20060">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        20010
                    </trace>
                    <trace class="Integer" >
                        20060
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:419" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700918000397 with request at line 20010 and response at line 20060">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        20010
                    </trace>
                    <trace class="Integer" >
                        20060
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:422" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700918000397 with request at line 20010 and response at line 20060">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        20010
                    </trace>
                    <trace class="Integer" >
                        20060
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:425" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000397 with request at line 20010 and response at line 20060">
                    <trace class="String" >
                        700918000397
                    </trace>
                    <trace class="Integer" >
                        20010
                    </trace>
                    <trace class="Integer" >
                        20060
                    </trace>
                </warning>
                <elapsed_time milliseconds="11"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:428" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:429" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:430" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:431" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:432" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:433" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:434" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="19"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:434" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:434" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:435" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 700918000399 with request at line 20099 and response at line 20189">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20099
                    </trace>
                    <trace class="Integer" >
                        20189
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:438" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000399 with request at line 20099 and response at line 20189">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20099
                    </trace>
                    <trace class="Integer" >
                        20189
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:441" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:441" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:443" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:444" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:444" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:445" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:446" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:447" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:447" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:448" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 700918000399 with request at line 20235 and response at line 20285">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20235
                    </trace>
                    <trace class="Integer" >
                        20285
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:450" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 700918000399 with request at line 20235 and response at line 20285">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20235
                    </trace>
                    <trace class="Integer" >
                        20285
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:453" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 700918000399 with request at line 20235 and response at line 20285">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20235
                    </trace>
                    <trace class="Integer" >
                        20285
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:455" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 700918000399 with request at line 20235 and response at line 20285">
                    <trace class="String" >
                        700918000399
                    </trace>
                    <trace class="Integer" >
                        20235
                    </trace>
                    <trace class="Integer" >
                        20285
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:458" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:459" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:460" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:461" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:462" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:463" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:464" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:464" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:464" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:465" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000401 with request at line 20324 and response at line 20414">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20324
                    </trace>
                    <trace class="Integer" >
                        20414
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:467" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000401 with request at line 20324 and response at line 20414">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20324
                    </trace>
                    <trace class="Integer" >
                        20414
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:470" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:471" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:472" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:473" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:474" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:475" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:476" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:476" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:477" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:477" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000401 with request at line 20460 and response at line 20510">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20460
                    </trace>
                    <trace class="Integer" >
                        20510
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:480" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000401 with request at line 20460 and response at line 20510">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20460
                    </trace>
                    <trace class="Integer" >
                        20510
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:482" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000401 with request at line 20460 and response at line 20510">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20460
                    </trace>
                    <trace class="Integer" >
                        20510
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:484" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000401 with request at line 20460 and response at line 20510">
                    <trace class="String" >
                        701008000401
                    </trace>
                    <trace class="Integer" >
                        20460
                    </trace>
                    <trace class="Integer" >
                        20510
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:487" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:488" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:489" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:490" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:491" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:492" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:493" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:494" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:494" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:494" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000403 with request at line 20549 and response at line 20639">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20549
                    </trace>
                    <trace class="Integer" >
                        20639
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:497" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000403 with request at line 20549 and response at line 20639">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20549
                    </trace>
                    <trace class="Integer" >
                        20639
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:500" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:502" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:503" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:504" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:505" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:506" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:507" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="14"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:507" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:508" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:508" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000403 with request at line 20685 and response at line 20735">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20685
                    </trace>
                    <trace class="Integer" >
                        20735
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:511" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000403 with request at line 20685 and response at line 20735">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20685
                    </trace>
                    <trace class="Integer" >
                        20735
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:513" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000403 with request at line 20685 and response at line 20735">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20685
                    </trace>
                    <trace class="Integer" >
                        20735
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:515" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000403 with request at line 20685 and response at line 20735">
                    <trace class="String" >
                        701008000403
                    </trace>
                    <trace class="Integer" >
                        20685
                    </trace>
                    <trace class="Integer" >
                        20735
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:517" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:518" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:519" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:520" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:521" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:522" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:523" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:523" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:524" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:524" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000405 with request at line 20774 and response at line 20864">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20774
                    </trace>
                    <trace class="Integer" >
                        20864
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:526" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000405 with request at line 20774 and response at line 20864">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20774
                    </trace>
                    <trace class="Integer" >
                        20864
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:528" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:529" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:531" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:532" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:532" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:533" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:534" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:534" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:535" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:535" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000405 with request at line 20910 and response at line 20960">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20910
                    </trace>
                    <trace class="Integer" >
                        20960
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:537" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000405 with request at line 20910 and response at line 20960">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20910
                    </trace>
                    <trace class="Integer" >
                        20960
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:540" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000405 with request at line 20910 and response at line 20960">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20910
                    </trace>
                    <trace class="Integer" >
                        20960
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:542" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000405 with request at line 20910 and response at line 20960">
                    <trace class="String" >
                        701008000405
                    </trace>
                    <trace class="Integer" >
                        20910
                    </trace>
                    <trace class="Integer" >
                        20960
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:544" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:545" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:546" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:547" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:548" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:549" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:550" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:550" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:551" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:551" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000407 with request at line 20999 and response at line 21089">
                    <trace class="String" >
                        701008000407
                    </trace>
                    <trace class="Integer" >
                        20999
                    </trace>
                    <trace class="Integer" >
                        21089
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:554" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000407 with request at line 20999 and response at line 21089">
                    <trace class="String" >
                        701008000407
                    </trace>
                    <trace class="Integer" >
                        20999
                    </trace>
                    <trace class="Integer" >
                        21089
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:556" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:557" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:558" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:559" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:560" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:561" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:561" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:562" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:562" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:563" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000409 with request at line 21135 and response at line 21225">
                    <trace class="String" >
                        701008000409
                    </trace>
                    <trace class="Integer" >
                        21135
                    </trace>
                    <trace class="Integer" >
                        21225
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:565" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000409 with request at line 21135 and response at line 21225">
                    <trace class="String" >
                        701008000409
                    </trace>
                    <trace class="Integer" >
                        21135
                    </trace>
                    <trace class="Integer" >
                        21225
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:567" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:568" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:569" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:570" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:571" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:572" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:573" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:573" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:574" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:574" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000411 with request at line 21271 and response at line 21361">
                    <trace class="String" >
                        701008000411
                    </trace>
                    <trace class="Integer" >
                        21271
                    </trace>
                    <trace class="Integer" >
                        21361
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:576" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000411 with request at line 21271 and response at line 21361">
                    <trace class="String" >
                        701008000411
                    </trace>
                    <trace class="Integer" >
                        21271
                    </trace>
                    <trace class="Integer" >
                        21361
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:578" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:579" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:580" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:581" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:582" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:583" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:584" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:584" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:584" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:585" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000413 with request at line 21407 and response at line 21497">
                    <trace class="String" >
                        701008000413
                    </trace>
                    <trace class="Integer" >
                        21407
                    </trace>
                    <trace class="Integer" >
                        21497
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:588" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000413 with request at line 21407 and response at line 21497">
                    <trace class="String" >
                        701008000413
                    </trace>
                    <trace class="Integer" >
                        21407
                    </trace>
                    <trace class="Integer" >
                        21497
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:590" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:591" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:592" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:593" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:593" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:594" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:595" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:596" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:596" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:597" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000415 with request at line 21543 and response at line 21633">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21543
                    </trace>
                    <trace class="Integer" >
                        21633
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:599" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000415 with request at line 21543 and response at line 21633">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21543
                    </trace>
                    <trace class="Integer" >
                        21633
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:601" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:602" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:603" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:605" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:605" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:607" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:607" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:608" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:608" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:609" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000415 with request at line 21679 and response at line 21729">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21679
                    </trace>
                    <trace class="Integer" >
                        21729
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:611" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000415 with request at line 21679 and response at line 21729">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21679
                    </trace>
                    <trace class="Integer" >
                        21729
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:613" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000415 with request at line 21679 and response at line 21729">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21679
                    </trace>
                    <trace class="Integer" >
                        21729
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:616" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000415 with request at line 21679 and response at line 21729">
                    <trace class="String" >
                        701008000415
                    </trace>
                    <trace class="Integer" >
                        21679
                    </trace>
                    <trace class="Integer" >
                        21729
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:618" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:619" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:621" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:622" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:622" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:623" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:624" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:625" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:625" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:626" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:627" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:628" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:629" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:629" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:630" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:631" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:632" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:632" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:633" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:634" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:635" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:636" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:637" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:638" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:639" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:639" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:640" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:640" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:641" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:642" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:644" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:644" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:646" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:646" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:647" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:647" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:648" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:649" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:650" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:651" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:652" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:653" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:654" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:654" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:655" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:655" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:656" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:658" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:659" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:659" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:660" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:661" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:662" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:662" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:663" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000427 with request at line 22363 and response at line 22453">
                    <trace class="String" >
                        701008000427
                    </trace>
                    <trace class="Integer" >
                        22363
                    </trace>
                    <trace class="Integer" >
                        22453
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:665" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000427 with request at line 22363 and response at line 22453">
                    <trace class="String" >
                        701008000427
                    </trace>
                    <trace class="Integer" >
                        22363
                    </trace>
                    <trace class="Integer" >
                        22453
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:667" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:669" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:670" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:671" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:672" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:673" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:673" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:674" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:674" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:675" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000429 with request at line 22499 and response at line 22589">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22499
                    </trace>
                    <trace class="Integer" >
                        22589
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:677" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000429 with request at line 22499 and response at line 22589">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22499
                    </trace>
                    <trace class="Integer" >
                        22589
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:680" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:681" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:682" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:683" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:684" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:685" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:685" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:686" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:687" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:687" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000429 with request at line 22635 and response at line 22685">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22635
                    </trace>
                    <trace class="Integer" >
                        22685
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:689" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000429 with request at line 22635 and response at line 22685">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22635
                    </trace>
                    <trace class="Integer" >
                        22685
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:691" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000429 with request at line 22635 and response at line 22685">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22635
                    </trace>
                    <trace class="Integer" >
                        22685
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:695" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000429 with request at line 22635 and response at line 22685">
                    <trace class="String" >
                        701008000429
                    </trace>
                    <trace class="Integer" >
                        22635
                    </trace>
                    <trace class="Integer" >
                        22685
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:697" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:697" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:698" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:699" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:700" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:701" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:701" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:702" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:702" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:703" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000431 with request at line 22724 and response at line 22814">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22724
                    </trace>
                    <trace class="Integer" >
                        22814
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:706" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000431 with request at line 22724 and response at line 22814">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22724
                    </trace>
                    <trace class="Integer" >
                        22814
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:708" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:709" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:711" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:711" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:712" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:713" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:714" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:714" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:715" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:715" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000431 with request at line 22860 and response at line 22910">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22860
                    </trace>
                    <trace class="Integer" >
                        22910
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:717" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000431 with request at line 22860 and response at line 22910">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22860
                    </trace>
                    <trace class="Integer" >
                        22910
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:720" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000431 with request at line 22860 and response at line 22910">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22860
                    </trace>
                    <trace class="Integer" >
                        22910
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:722" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000431 with request at line 22860 and response at line 22910">
                    <trace class="String" >
                        701008000431
                    </trace>
                    <trace class="Integer" >
                        22860
                    </trace>
                    <trace class="Integer" >
                        22910
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:724" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:725" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:726" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:727" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:728" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:729" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:730" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:730" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:730" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:731" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000433 with request at line 22949 and response at line 23039">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        22949
                    </trace>
                    <trace class="Integer" >
                        23039
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:733" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000433 with request at line 22949 and response at line 23039">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        22949
                    </trace>
                    <trace class="Integer" >
                        23039
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:735" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:736" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:737" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:738" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:739" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:741" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:742" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:742" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:742" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:743" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701008000433 with request at line 23085 and response at line 23135">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        23085
                    </trace>
                    <trace class="Integer" >
                        23135
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:745" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701008000433 with request at line 23085 and response at line 23135">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        23085
                    </trace>
                    <trace class="Integer" >
                        23135
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:747" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701008000433 with request at line 23085 and response at line 23135">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        23085
                    </trace>
                    <trace class="Integer" >
                        23135
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:749" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000433 with request at line 23085 and response at line 23135">
                    <trace class="String" >
                        701008000433
                    </trace>
                    <trace class="Integer" >
                        23085
                    </trace>
                    <trace class="Integer" >
                        23135
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:751" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:752" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:753" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:754" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:755" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:756" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:757" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:757" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:758" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:758" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000435 with request at line 23174 and response at line 23264">
                    <trace class="String" >
                        701008000435
                    </trace>
                    <trace class="Integer" >
                        23174
                    </trace>
                    <trace class="Integer" >
                        23264
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:760" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000435 with request at line 23174 and response at line 23264">
                    <trace class="String" >
                        701008000435
                    </trace>
                    <trace class="Integer" >
                        23174
                    </trace>
                    <trace class="Integer" >
                        23264
                    </trace>
                </warning>
                <elapsed_time milliseconds="4"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:763" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:764" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:765" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:766" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:766" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:767" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:768" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:768" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:769" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:769" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701008000435 with request at line 23310 and response at line 23360">
                    <trace class="String" >
                        701008000435
                    </trace>
                    <trace class="Integer" >
                        23310
                    </trace>
                    <trace class="Integer" >
                        23360
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:772" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701008000435 with request at line 23310 and response at line 23360">
                    <trace class="String" >
                        701008000435
                    </trace>
                    <trace class="Integer" >
                        23310
                    </trace>
                    <trace class="Integer" >
                        23360
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:774" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:775" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:776" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:777" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:778" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:779" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:780" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:780" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:780" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:781" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701009000437 with request at line 23401 and response at line 23491">
                    <trace class="String" >
                        701009000437
                    </trace>
                    <trace class="Integer" >
                        23401
                    </trace>
                    <trace class="Integer" >
                        23491
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:783" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701009000437 with request at line 23401 and response at line 23491">
                    <trace class="String" >
                        701009000437
                    </trace>
                    <trace class="Integer" >
                        23401
                    </trace>
                    <trace class="Integer" >
                        23491
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:785" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:786" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:788" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:789" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:789" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:790" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:791" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:791" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:792" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:792" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701009000438 with request at line 23537 and response at line 23587">
                    <trace class="String" >
                        701009000438
                    </trace>
                    <trace class="Integer" >
                        23537
                    </trace>
                    <trace class="Integer" >
                        23587
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:794" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701009000438 with request at line 23537 and response at line 23587">
                    <trace class="String" >
                        701009000438
                    </trace>
                    <trace class="Integer" >
                        23537
                    </trace>
                    <trace class="Integer" >
                        23587
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:797" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:798" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:800" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:801" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:801" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:803" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:804" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:804" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:804" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:805" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701009000439 with request at line 23628 and response at line 23718">
                    <trace class="String" >
                        701009000439
                    </trace>
                    <trace class="Integer" >
                        23628
                    </trace>
                    <trace class="Integer" >
                        23718
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:807" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701009000439 with request at line 23628 and response at line 23718">
                    <trace class="String" >
                        701009000439
                    </trace>
                    <trace class="Integer" >
                        23628
                    </trace>
                    <trace class="Integer" >
                        23718
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:809" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:810" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:812" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:813" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:813" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:814" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:815" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:816" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:816" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:817" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701009000441 with request at line 23764 and response at line 23854">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23764
                    </trace>
                    <trace class="Integer" >
                        23854
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:819" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701009000441 with request at line 23764 and response at line 23854">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23764
                    </trace>
                    <trace class="Integer" >
                        23854
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:822" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:823" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:824" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:825" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:825" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:826" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:827" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:827" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:828" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:828" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701009000441 with request at line 23900 and response at line 23950">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23900
                    </trace>
                    <trace class="Integer" >
                        23950
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:831" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701009000441 with request at line 23900 and response at line 23950">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23900
                    </trace>
                    <trace class="Integer" >
                        23950
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:833" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701009000441 with request at line 23900 and response at line 23950">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23900
                    </trace>
                    <trace class="Integer" >
                        23950
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:836" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701009000441 with request at line 23900 and response at line 23950">
                    <trace class="String" >
                        701009000441
                    </trace>
                    <trace class="Integer" >
                        23900
                    </trace>
                    <trace class="Integer" >
                        23950
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:838" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:839" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:840" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:841" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:842" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:843" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:843" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="17"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:844" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:845" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:845" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:846" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:848" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:848" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:849" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:850" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:851" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:851" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:852" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:853" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:854" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:855" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:856" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:857" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:858" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:859" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:860" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:860" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:861" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:862" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:863" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:864" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:865" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:866" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:867" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="7"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:867" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:868" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:869" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:870" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:871" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:872" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:873" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:874" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:875" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="9"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:876" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:876" line="130" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:877" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:878" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:880" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:881" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:881" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:882" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:883" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="8"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:884" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:884" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:885" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701114000453 with request at line 24584 and response at line 24674">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24584
                    </trace>
                    <trace class="Integer" >
                        24674
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:887" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000453 with request at line 24584 and response at line 24674">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24584
                    </trace>
                    <trace class="Integer" >
                        24674
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:890" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:891" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:892" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:893" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:894" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:896" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:896" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="13"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:897" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:897" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:898" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701114000453 with request at line 24720 and response at line 24770">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24720
                    </trace>
                    <trace class="Integer" >
                        24770
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:900" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701114000453 with request at line 24720 and response at line 24770">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24720
                    </trace>
                    <trace class="Integer" >
                        24770
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:903" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701114000453 with request at line 24720 and response at line 24770">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24720
                    </trace>
                    <trace class="Integer" >
                        24770
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:905" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000453 with request at line 24720 and response at line 24770">
                    <trace class="String" >
                        701114000453
                    </trace>
                    <trace class="Integer" >
                        24720
                    </trace>
                    <trace class="Integer" >
                        24770
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:907" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:908" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:909" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:910" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:911" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:912" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:912" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:913" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:913" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:913" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701114000455 with request at line 24809 and response at line 24899">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24809
                    </trace>
                    <trace class="Integer" >
                        24899
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:916" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000455 with request at line 24809 and response at line 24899">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24809
                    </trace>
                    <trace class="Integer" >
                        24899
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:918" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:919" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:920" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:921" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:922" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:923" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:923" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:924" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:924" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:925" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701114000455 with request at line 24945 and response at line 24995">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24945
                    </trace>
                    <trace class="Integer" >
                        24995
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:928" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701114000455 with request at line 24945 and response at line 24995">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24945
                    </trace>
                    <trace class="Integer" >
                        24995
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:930" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701114000455 with request at line 24945 and response at line 24995">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24945
                    </trace>
                    <trace class="Integer" >
                        24995
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:932" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000455 with request at line 24945 and response at line 24995">
                    <trace class="String" >
                        701114000455
                    </trace>
                    <trace class="Integer" >
                        24945
                    </trace>
                    <trace class="Integer" >
                        24995
                    </trace>
                </warning>
                <elapsed_time milliseconds="10"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:934" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:935" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:936" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:937" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:938" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:939" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:940" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:941" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:941" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:942" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701114000457 with request at line 25034 and response at line 25124">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25034
                    </trace>
                    <trace class="Integer" >
                        25124
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:944" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000457 with request at line 25034 and response at line 25124">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25034
                    </trace>
                    <trace class="Integer" >
                        25124
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:946" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:947" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:949" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:950" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:950" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:951" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:952" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="11"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:952" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:953" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:953" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701114000457 with request at line 25170 and response at line 25220">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25170
                    </trace>
                    <trace class="Integer" >
                        25220
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:955" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701114000457 with request at line 25170 and response at line 25220">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25170
                    </trace>
                    <trace class="Integer" >
                        25220
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:957" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701114000457 with request at line 25170 and response at line 25220">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25170
                    </trace>
                    <trace class="Integer" >
                        25220
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:959" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701114000457 with request at line 25170 and response at line 25220">
                    <trace class="String" >
                        701114000457
                    </trace>
                    <trace class="Integer" >
                        25170
                    </trace>
                    <trace class="Integer" >
                        25220
                    </trace>
                </warning>
                <elapsed_time milliseconds="8"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:961" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:962" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:963" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:964" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:965" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:966" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:966" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="15"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:967" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:967" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:968" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701117000459 with request at line 25259 and response at line 25349">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25259
                    </trace>
                    <trace class="Integer" >
                        25349
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:970" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701117000459 with request at line 25259 and response at line 25349">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25259
                    </trace>
                    <trace class="Integer" >
                        25349
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:972" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:974" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:975" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:976" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:977" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:978" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:978" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:979" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:979" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:980" line="130" exception_class="T_visa_recon_generator" message="F005 is missing for transaction with RRN 701117000459 with request at line 25395 and response at line 25445">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25395
                    </trace>
                    <trace class="Integer" >
                        25445
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:982" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for Clearing transaction with RRN 701117000459 with request at line 25395 and response at line 25445">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25395
                    </trace>
                    <trace class="Integer" >
                        25445
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:984" line="130" exception_class="T_visa_recon_generator" message="F050 is missing for Clearing transaction with RRN 701117000459 with request at line 25395 and response at line 25445">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25395
                    </trace>
                    <trace class="Integer" >
                        25445
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:986" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701117000459 with request at line 25395 and response at line 25445">
                    <trace class="String" >
                        701117000459
                    </trace>
                    <trace class="Integer" >
                        25395
                    </trace>
                    <trace class="Integer" >
                        25445
                    </trace>
                </warning>
                <elapsed_time milliseconds="9"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:47:988" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:990" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:991" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:992" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:993" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:47:994" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:47:994" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </PostfixExpression>
            <elapsed_time milliseconds="16"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:47:995" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:47:995" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:47:996" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701117000461 with request at line 25484 and response at line 25574">
                    <trace class="String" >
                        701117000461
                    </trace>
                    <trace class="Integer" >
                        25484
                    </trace>
                    <trace class="Integer" >
                        25574
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:47:998" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701117000461 with request at line 25484 and response at line 25574">
                    <trace class="String" >
                        701117000461
                    </trace>
                    <trace class="Integer" >
                        25484
                    </trace>
                    <trace class="Integer" >
                        25574
                    </trace>
                </warning>
                <elapsed_time milliseconds="5"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:48:001" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="2"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:002" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:003" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:004" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:005" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:006" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:48:007" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <iteration datetimestamp="2017-03-14 23:39:48:007" line="129" exception_class="T_visa_recon_generator">
            <validate_merged_transaction datetimestamp="2017-03-14 23:39:48:008" line="130" exception_class="T_visa_recon_generator">
                <warning datetimestamp="2017-03-14 23:39:48:008" line="130" exception_class="T_visa_recon_generator" message="F006 is missing for transaction with RRN 701214000463 with request at line 25620 and response at line 25710">
                    <trace class="String" >
                        701214000463
                    </trace>
                    <trace class="Integer" >
                        25620
                    </trace>
                    <trace class="Integer" >
                        25710
                    </trace>
                </warning>
                <warning datetimestamp="2017-03-14 23:39:48:010" line="130" exception_class="T_visa_recon_generator" message="F051 is missing for transaction with RRN 701214000463 with request at line 25620 and response at line 25710">
                    <trace class="String" >
                        701214000463
                    </trace>
                    <trace class="Integer" >
                        25620
                    </trace>
                    <trace class="Integer" >
                        25710
                    </trace>
                </warning>
                <elapsed_time milliseconds="6"/>
            </validate_merged_transaction>
            <write_line datetimestamp="2017-03-14 23:39:48:013" line="131" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:014" line="132" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:015" line="133" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:016" line="134" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:016" line="135" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <write_line datetimestamp="2017-03-14 23:39:48:018" line="136" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="1"/>
            </write_line>
            <PostfixExpression datetimestamp="2017-03-14 23:39:48:019" line="137" exception_class="T_visa_recon_generator">
                <elapsed_time milliseconds="0"/>
            </PostfixExpression>
            <elapsed_time milliseconds="12"/>
        </iteration>
        <elapsed_time milliseconds="2691"/>
    </for>
    <write_line datetimestamp="2017-03-14 23:39:48:019" line="139" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="2"/>
    </write_line>
    <write_line datetimestamp="2017-03-14 23:39:48:021" line="140" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="1"/>
    </write_line>
    <write_line datetimestamp="2017-03-14 23:39:48:022" line="141" exception_class="T_visa_recon_generator">
        <elapsed_time milliseconds="1"/>
    </write_line>
    <elapsed_time milliseconds="3673"/>
</convert_vts_log_to_ctf>
```
