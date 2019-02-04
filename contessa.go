package main

import (
	"flag"
	"fmt"
	"log"
	"os"
	"os/signal"

	"github.com/spf13/viper"
)

// Version of Contessa
const Version = "v0.14.0"

var (
	showVersion bool
	configFile  string
	baseDir     string
)

func main() {

	log.Printf("Starting Contessa %s...", Version)

	// Startup flags and parameters, can be seen with `--help`.
	flag.BoolVar(&showVersion, "version", false, "show current version")
	flag.StringVar(&configFile, "config", "./config.yaml", "configuration file path")
	flag.StringVar(&baseDir, "base-dir", "", "base working dir")
	flag.Parse()

	// Optional version information output
	if showVersion {
		fmt.Println(Version)
		os.Exit(0)
	}

	// Begin setup and initialization
	viper.SetConfigFile(configFile)
	if err := viper.ReadInConfig(); err != nil {
		panic(fmt.Errorf("fatal error config file: %s", err))
	}

	// A provided `--base-dir` parameter overrules the configuration
	if baseDir == "" {
		baseDir = viper.GetString("base-dir")
	}

	// Strict requirement, must be enforced
	if baseDir == "" {
		panic(fmt.Errorf("missing required configuration property `base-dir`"))
	}

	log.Printf("Read configuration, will use base-dir: %s", baseDir)

	// Now we'll initialize storage backends
	log.Printf("storage.file.enabled: %v", viper.GetBool("storage.file.enabled"))

	waitUntilInterrupt()
	log.Println("Shutting down...")

	log.Println("Contessage gracefully shut down")
}

func waitUntilInterrupt() {
	trap := make(chan os.Signal, 1)
	signal.Notify(trap, os.Interrupt)
	<-trap
}
