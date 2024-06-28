#!/bin/bash

#You have to change this to the updated file path
all_awards_csv_path="../src/main/resources/static/academy_awards.csv"
best_picture_csv_path="../src/main/resources/static/best_pictures.csv"

grep "Best Picture" "$all_awards_csv_path" > "$best_picture_csv_path"

echo "Press smth"
read