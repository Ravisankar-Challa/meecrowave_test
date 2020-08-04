For tomcat create one with out keypass
keytool -genkeypair -alias ravi_key_and_certificate -keystore ravi.p12 -storepass sankar -storetype PKCS12 -keyalg RSA -keysize 2048 -validity 36500 -dname "CN=localhost, OU=IT, O=TC, L=Sydney, S=NSW, C=AU" -v

Export X509 Public KeyCertificate
openssl pkcs12 -in /home/ravi/ravi.p12 -nokeys -out cert.pem

Export unencrypted private key
openssl pkcs12 -in /home/ravi/ravi.p12 -nodes -nocerts -out key.pem

keytool -genkeypair -alias ravi_key_and_certificate -keypass sankar -keystore ravi.p12 -storepass sankar -storetype PKCS12 -keyalg RSA -keysize 2048 -validity 36500 -dname "CN=localhost, OU=IT, O=TC, L=Sydney, S=NSW, C=AU" -v

-Djava.util.concurrent.ForkJoinPool.common.parallelism=5

mkdir ca
cd ca
cp /usr/lib/ssl/openssl.cnf .
change in openssl.cnf
[ CA_default ]
# Directory and file locations.
dir               = /root/ca
to current working directory
mkdir newcerts certs crl private requests
touch index.txt
echo 4000 > serial

openssl genrsa -aes256 -out private/cakey.pem 4096
Use the root key (ca.key.pem) to create a root certificate (ca.cert.pem).
openssl req -config openssl.cnf \
      -key private/cakey.pem \
      -new -x509 -days 9300 -sha256 -extensions v3_ca \
      -out certs/cacert.pem
verify cert - openssl x509 -noout -text -in certs/cacert.pem

Create the intermediate certificates
mkdir intermediate
cd intermediate
cp ../openssl.cnf .
change in openssl.cnf
[ CA_default ]
# Directory and file locations.
dir               = /root/ca
to current working directory
private_key     = $dir/private/intermediate.cakey.pem
certificate     = $dir/certs/intermediate.cacert.pem
crl             = $dir/crl/intermediate.crl.pem
policy          = policy_anything

mkdir newcerts certs csr crl private requests
touch index.txt
echo 4000 > serial
Add a crlnumber file to the intermediate CA directory tree. crlnumber is used to keep track of certificate revocation lists.
echo 1000 > crlnumber
openssl genrsa -aes256 -out private/intermediate.cakey.pem 4096
openssl req -config openssl.cnf -new -sha256 \
      -key private/intermediate.cakey.pem \
      -out csr/intermediate.csr.pem
cd ..
openssl ca -config openssl.cnf \
      -days 8900 -notext -md sha256 \
      -in intermediate/csr/intermediate.csr.pem \
      -out intermediate/certs/intermediate.cacert.pem   

As we did for the root certificate, check that the details of the intermediate certificate are correct.
openssl x509 -noout -text -in intermediate/certs/intermediate.cacert.pem

Verify the intermediate certificate against the root certificate. An OK indicates that the chain of trust is intact.
openssl verify -CAfile certs/cacert.pem \
      intermediate/certs/intermediate.cacert.pem

Create the certificate chain file
cat intermediate/certs/intermediate.cacert.pem \
      certs/cacert.pem > intermediate/certs/ca-chain.cert.pem

Create servet key and certs
openssl genrsa -aes256 -out meecrowave.key.pem 2048
openssl req -new -key meecrowave.key.pem -sha256 -out meecrowave.csr.pem
cd ..   
openssl ca -config intermediate/openssl.cnf \
      -days 9990 -notext -md sha256 \
      -in server/meecrowave.csr.pem \
      -out server/meecrowave.cert.pem
openssl x509 -noout -text -in server/meecrowave.cert.pem
Remove private key password
openssl rsa -in server/meecrowave.key.pem -out server/meecrowave.key1.pem
convert from traditional format to pkcs8
-----BEGIN RSA PRIVATE KEY-----
-----END RSA PRIVATE KEY-----
to
-----BEGIN PRIVATE KEY-----
-----END PRIVATE KEY-----
openssl pkcs8 -topk8 -inform pem -in server/meecrowave.key1.pem -outform pem -nocrypt -out server/meecrowave.key2.pem
