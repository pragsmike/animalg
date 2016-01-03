# ClojureScript Algorithm animation

A toy for learning about ClojureScript, using [boot](https://github.com/boot-clj/boot) 
for build/run/repl and [reagent](https://reagent-project.github.io/) for rendering in ReactJS.

I highly recommend the [Modern ClojureScript tutorial](https://github.com/magomimmo/modern-cljs/blob/master/doc/second-edition/tutorial-01.md)
for an in-depth treatment of how to use boot to develop browser-side ClojureScript.

This animates various sorting algorithms.
Right now it only does bubble sort.

```
boot dev
```

will start an HTTP server to serve up the static assets (including the
compiled JavaScript), 
start a REPL server ([boot-cljs-repl](https://github.com/adzerk-oss/boot-cljs-repl)), 
and compile the cljs into JavaScript ([boot-cljs](https://github.com/adzerk-oss/boot-cljs)).
As you edit cljs source, it will get automatically built and loaded into
the browser ([boot-reload](https://github.com/adzerk-oss/boot-reload)).

Note that this process is not interactive -- it's not the REPL client and you can't type at it.
However, the process does start an nREPL *server* that you can connect to with a REPL client,
such as boot repl, Emacs Cider or IntelliJ Cursive.  Look for the port number that it prints
as it starts, and specify that port to your REPL client.

Once you're connected to the REPL, you'll be talking to the Clojure process
where boot has started the nREPL server.  You're not yet talking to the CLJS
REPL that would evaluate expressions in the browser (the "browser REPL").
To start such a browser REPL, in the first REPL do

```
(start-repl)
```

Point a browser at http://localhost:3000/ to see the application page.

## A note about network ports

You'll notice that the above steps seem to start a scary number of network
listeners.  Look at this output from `boot dev`:

```
Writing boot_cljs_repl.cljs...
Starting reload server on ws://localhost:55469
Writing boot_reload.cljs...
2015-12-19 15:10:30.925:INFO::clojure-agent-send-off-pool-0: Logging initialized @17014ms
Directory 'target' was not found. Creating it...2015-12-19 15:10:31.036:INFO:oejs.Server:clojure-agent-send-off-pool-0: jetty-9.2.10.v20150310
2015-12-19 15:10:31.094:INFO:oejs.ServerConnector:clojure-agent-send-off-pool-0: Started ServerConnector@23672fab{HTTP/1.1}{0.0.0.0:3000}
2015-12-19 15:10:31.096:INFO:oejs.Server:clojure-agent-send-off-pool-0: Started @17185ms
Started Jetty on http://localhost:3000

Starting file watcher (CTRL-C to quit)...

nREPL server started on port 47967 on host 127.0.0.1 - nrepl://127.0.0.1:47967
Adding :require adzerk.boot-cljs-repl to app.cljs.edn...
Adding :require adzerk.boot-reload to app.cljs.edn...
Compiling ClojureScript...
• js/app.js
Elapsed time: 20.929 sec
```

We see 
- ws://localhost:55469
- 0.0.0.0:3000
- nrepl://127.0.0.1:47967

Of these, you only need know the last two.

The nrepl port is what you tell your REPL client (emacs cider, IntelliJ, boot repl) to connect to.
The :3000 port is what you tell your web browser to visit, in order to see the application's page.


## TODO

More algorithms!  Define a protocol to make it easy to plug in other algorithms.
Even the existing bubble sort could be made smarter, because it still looks at
all entries, including ones at the end of the vector, which are known to be sorted already.

Indicate the state of the algorithm by drawing certain items in different colors.

