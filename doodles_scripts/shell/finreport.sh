#!/bin/bash
cat iq4.txt | sed 's/Ԥ��/INC/g' | awk '{print $6}'