1. Add https and some api token provider
2. In the real pipeline i'd add some cache for m2, because now, if you want to build and test this app with dockerfile, all dependencies are downloaded for every build run.
3. 