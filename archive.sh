#!/bin/sh

git archive \
    --format zip \
    --output ../squarespace-java-interview.zip \
    --prefix=squarespace-java-interview/ \
    master

echo 'DONE'
