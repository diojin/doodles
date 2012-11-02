#!/bin/bash
cat iq4.txt | sed 's/ิคิ๖/INC/g' | awk '{print $6}'