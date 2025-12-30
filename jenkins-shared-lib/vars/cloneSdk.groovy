// vars/cloneSdk.groovy
def call(sdkCfg, dirName) {
    dir(dirName) {
        checkout([
            $class: 'GitSCM',
            branches: [[name: sdkCfg.branch]],
            userRemoteConfigs: [[url: sdkCfg.repoUrl]],
            extensions: sdkCfg.submodules ? [
                [$class: 'SubmoduleOption',
                 recursiveSubmodules: true,
                 trackingSubmodules: false]
            ] : []
        ])
    }
}
