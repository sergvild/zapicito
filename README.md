# zapicito
Zapicito


1. Run docker login -u fiphiker

2. At the password prompt, enter the personal access token.

dckr_pat_u2dAC1e-n_y4QhsDd_FfFOoBMVw

docker login -u fiphiker -p dckr_pat_u2dAC1e-n_y4QhsDd_FfFOoBMVw

docker build --tag=zapicito:latest .

docker-compose up --build
