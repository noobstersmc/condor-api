package net.noobsters.api.condor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * CondorApi
 */
public class CondorApi {
    void d() {
        try {
            var bytes = downloadRaw();
            var file = Paths.get(System.getProperty("user.dir") + File.separatorChar + "lettuce.tar.gz").toFile();

            Files.write(bytes, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] downloadRaw() throws Exception {
        var url = new URL(
                "https://github.com/lettuce-io/lettuce-core/releases/download/6.0.3.RELEASE/lettuce-core-6.0.3.RELEASE-bin.tar.gz");
        var connection = url.openConnection();

        connection.setRequestProperty("User-Agent", "luckperms");
        connection.setConnectTimeout(1000 * 10);
        connection.setReadTimeout(1000 * 10);

        try (InputStream in = connection.getInputStream()) {
            byte[] bytes = ByteStreams.toByteArray(in);
            if (bytes.length == 0) {
                throw new Exception("Empty stream");
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void unzipFile(File file) throws Exception{
       var inputStream =  new GZIPInputStream(new FileInputStream(file));
       new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(inputStream)));
       inputStream.close();
       
    }

    public static void main(String[] args) {
        var condor = new CondorApi();

        condor.d();
    }

}