def call(testCfg) {
    testCfg.YAML.each {
        sh """
        cd cli/scripts
        th-cli project create --name DemoProject
        th-cli run-tests -t TC_EEM_2_1 -p /home/ubuntu/1_5_Final_Validated_and_Generated_XML_files/  --title "TC-EEM-2_1" --project-id 1 -c ../th_cli/default_config.properties >> TestRun.log
        """
    }
    testCfg.PYTHON.each {
        sh "python3 ${it}"
    }
}
