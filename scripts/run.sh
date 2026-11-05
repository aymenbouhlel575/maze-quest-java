#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
[[ -f build/MazeQuest.jar ]] || bash scripts/build.sh
java -jar build/MazeQuest.jar
