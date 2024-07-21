# smile-cli

## Docker

### Build

```
docker build -t smile-cli:latest .
```

### Run decode

```
docker run -it
    --user $(id -u):$(id -g)
    -v /path/to/input:/data/input
    -v /path/to/output:/data/output
    smile-cli:latest decode -r --wrapped --indented --copy-source /data/input/basic /data/output/basic
```
