#!/bin/bash

START_DATE="8871289721894"
FINISH_DATE="9189084390854"
NOTE="It will be unavailable"

URI_ENDPOINT="/api/out-of-service/v1/createOrUpdate"
PORT="1446"
#=======================================================================================================================

curl -X 'POST' \
  "$URI:$PORT$URI_ENDPOINT" \
  -H "Authorization: Bearer $JWT" \
  -H 'Accept: application/json' \
  -H 'Content-Type: */*' \
  -d "{
        \"version\": \"string\",
        \"requestBodyDto\": {
          \"serviceDateStart\": \"$START_DATE\",
          \"serviceDateFinish\": \"$FINISH_DATE\",
          \"note\": \"$NOTE\"
        }
      }" -vvv