function copyEnvVarsToGradleProperties{
  GRADLE_PROPERTIES=$HOME"/gradle.properties"
  export GRADLE_PROPERTIES
  echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

  if [ ! -f "$GRADLE_PROPERTIES" ]; then
    echo "Gradle Properties does not exist"

    echo "Creating Gradle Properties file..."
    touch $GRADLE_PROPERTIES

    echo "Writing ORG_GRADLE_PROJECT_API_TOKEN to gradle.properties..."
    echo "API_TOKEN=$API_TOKEN" >> $GRADLE_PROPERTIES
  fi
}