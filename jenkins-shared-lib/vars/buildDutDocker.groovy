def call(cfg) {
    sh """
    docker run --rm \\
      -v \$PWD/dut_sdk:/work \\
      -w /work ${cfg.ci_config.build_config.dut.docker_image} \\
      bash -c '
        scripts/examples/gn_build_example.sh \
        examples/all-clusters-app/linux \
        out/all-clusters-app \
        chip_inet_config_enable_ipv4=false chip_device_config_enable_wifipaf=true
      '
    """
}
