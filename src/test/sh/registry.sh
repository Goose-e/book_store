#!/bin/bash

LOGIN="userLogin"
USER_AGE=22
PASSWORD="12io21i21o"

URI="http://dev-goose"
URI_ENDPOINT="/api/v1/auth/signup"
PORT="8080"
#=======================================================================================================================

curl -X 'POST' \
  "$URI:$PORT$URI_ENDPOINT" \
  -H 'Accept: application/json' \
  -H 'Content-Type: */*' \
  -d "{
        \"version\": \"string\",
        \"requestBodyDto\": {
          \"login\": \"$LOGIN\",
          \"userAge\": \"$USER_AGE\",
          \"password\": \"$PASSWORD\"
        }
      }" -vvv