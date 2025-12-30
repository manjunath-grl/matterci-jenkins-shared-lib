def call(sdkCfg, dirName) {
    dir(dirName) {
        checkout([
            $class: 'GitSCM',
            branches: [[name: sdkCfg.branch]],
            userRemoteConfigs: [[url: sdkCfg.repoUrl]]
        ])
    }
}
