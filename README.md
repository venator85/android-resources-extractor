# android-resources-extractor

For each XML resource file `res/values<qualifier>/<file>.xml`, extracts the resources with given names `<name>` into `<res_out>/values<qualifier>/<prefix><file>.xml`, and adds a prefix to the resource name.

## Example:

`res/values-it/strings.xml`:

    <?xml version="1.0" encoding="UTF-8"?>
    <resources xmlns:android="http://schemas.android.com/apk/res/android">
        <string name="app_label">"Gestione download"</string>
        <string name="storage_description">"Download"</string>
        <string name="permlab_downloadManager">"Accesso alla gestione dei download."</string>
    </resources

When requested to extract `app_label`, add the file prefix `foo_` and the resource prefix `bar_`, you get:

`res/values-it/foo_strings.xml`:

    <?xml version="1.0" encoding="UTF-8"?>
    <resources xmlns:android="http://schemas.android.com/apk/res/android">
        <string name="bar_app_label">"Gestione download"</string>
    </resources

