def call(testCfg) {
    testCfg.PYTHON.each {
        def teatcase_name=it.split('/').last().replace('.py','')
        sh """
        cd cli/scripts
        th-cli project create --name DemoProject
        th-cli run-tests -t {$teatcase_name} -p /home/ubuntu/1_5_Final_Validated_and_Generated_XML_files/  --title "TC-EEM-2_1" --project-id 1 -c ../th_cli/default_config.properties >> ${home}/TestRun.log
        """
    }
    testCfg.YAML.each {
        sh "python3 ${it}"
    }
}
