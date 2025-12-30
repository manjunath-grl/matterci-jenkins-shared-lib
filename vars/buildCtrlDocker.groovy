def call(cfg) {
    sh """
    docker run --rm \\
      -v \$PWD/ctrl_sdk:/work \\
      -w /work ${cfg.ci_config.build_config.ctrl.docker_image} \\
      bash -c '
        scripts/examples/gn_build_example.sh examples/chip-tool out/chip-tool \
        chip_mdns="platform" chip_inet_config_enable_ipv4=false
      '
    """
}
