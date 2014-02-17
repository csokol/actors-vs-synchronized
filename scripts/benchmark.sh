

for i in {1..500}; do
    N="$((${i} * 100))"
    echo "concorrencia: $N"

    java -cp "lib/*:bin/:/home/csokol/eclipse/eclipse-jee/configuration/org.eclipse.osgi/bundles/910/1/.cp/lib/*" \
        br.com.caelum.almoco.progreativa.MainActors $N >> data/actors.csv

    java -cp "lib/*:bin/:/home/csokol/eclipse/eclipse-jee/configuration/org.eclipse.osgi/bundles/910/1/.cp/lib/*" \
        br.com.caelum.almoco.threads.MainThreads $N >> data/threads.csv
done
