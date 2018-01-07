# my-app

An example of how to setup a Clojure/ClojureScript project with a heavy toolset
for Test-Driven Development.

# External Dependencies
- Java 1.8.0_73
- Node v??
- Boot 2.7.1 (Build tool)

# Working on the application
- Start the development REPL
-- `boot my-app/start-cider-development-repl`
-- Leave it running
- Connect to the running REPL
- Run the dev test checks
-- `(dev/t)`

# Tasks
- See all available command line tasks for the application
-- `boot --help`

# Gotchas
- When connecting to the repl from cider: before starting the system, evaluate
an expression (e.g. `1`). This is necessary as it seems that cider will
re-evaluate all clojure files, overwriting any global declarations.

## License

Copyright © 2017 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
