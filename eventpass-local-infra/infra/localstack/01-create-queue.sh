#!/bin/sh
awslocal sqs create-queue --queue-name ordenes-pendientes >/dev/null
