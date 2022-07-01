# jhicli

## Node.js and NPM

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js](https://nodejs.org/): We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

## build

```bash
./mvnw clean package

echo "java -jar /usr/local/bin/jhicli.jar \"\$@\"" > target/jhicli
chmod +x target/jhicli
sudo mv target/{jhicli,jhicli.jar} /usr/local/bin/
source <(jhicli generate-completion)
```

remark: the completion is created from the module api on the server.
You can use option -s <server host> for generate completion for another server.


## help

Each command has a `-h` option for help

if you want the hep for apply the module postgresql you can use:

```bash
jhicli module apply postgresql -h

Usage: jhicli module apply postgresql [-hv] --basename=<basename> -p=project 
                                      folder --packagename=<packagename>
                                      [--prettierdefaultindent=<prettierdefaulti
                                      ndent>] [-s=<server>]
      --basename=<basename>
                          Project short name (only letters and numbers)
  -h, --help              Show usage help for the help command and exit.
  -p, --project-path=project folder
                          Project server path.
      --packagename=<packagename>
                          Base java package
      --prettierdefaultindent=<prettierdefaultindent>
                          Number of spaces in indentation
  -s, --server=<server>   server host path
  -v, --verbose           Print verbose output

```

## Usage

List modules available
```bash
jhicli module list
```

Describe a module
```bash
jhicli module describe < module name >
```

Apply a module
```bash
jhicli module apply < module name > OPTIONS
```


<!-- jhipster-needle-documentation -->

<!-- jhipster-needle-readme -->
