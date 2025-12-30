package com.matterci.pipelineLib
class YamlUtils {
    static void validate(def cfg) {
        if (!cfg?.ci_config) {
            throw new PipelineException("Invalid CI config")
        }
    }
}
