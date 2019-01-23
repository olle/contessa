package main

import (
	"flag"
	"fmt"
	"log"
	"os"

	ini "github.com/vaughan0/go-ini"
)

const version = "0.14.0"

var (
	config      ini.File
	configPath  string
	showVersion bool
)

func main() {
	log.Printf("Starting Contessa v%s...", version)

	flag.BoolVar(&showVersion, "version", false, "show current version")
	flag.StringVar(&configPath, "config", "./config.ini", "configuration file path")
	flag.Parse()

	if showVersion {
		fmt.Println(version)
		os.Exit(0)
	}

	_, err := ini.LoadFile(configPath)
	if err != nil {
		panic(err)
	}
}
