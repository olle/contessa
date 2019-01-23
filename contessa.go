package main

import (
	"flag"
	"fmt"
	"log"
	"os"

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

	// Startup flags and parameters
	flag.BoolVar(&showVersion, "version", false, "show current version")
	flag.StringVar(&configFile, "config", "./config.yaml", "configuration file path")
	flag.StringVar(&baseDir, "base-dir", "", "base working dir")
	flag.Parse()

	// Optional information output
	if showVersion {
		fmt.Println(Version)
		os.Exit(0)
	}

	// Begin setup and initialization
	viper.SetConfigFile(configFile)
	if err := viper.ReadInConfig(); err != nil {
		panic(fmt.Errorf("fatal error config file: %s", err))
	}

	if baseDir == "" {
		baseDir = viper.GetString("base-dir")
	}

	if baseDir == "" {
		panic(fmt.Errorf("missing required configuration property `base-dir`"))
	}

	log.Printf("Configuration base-dir: %s", baseDir)
}
