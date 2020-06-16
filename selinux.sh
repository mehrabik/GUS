#! /bin/sh
cp system/sepolicy/public/attributes system/sepolicy/prebuilts/api/29.0/public/attributes
cp system/sepolicy/public/service.te system/sepolicy/prebuilts/api/29.0/public/service.te
cp system/sepolicy/private/service_contexts system/sepolicy/prebuilts/api/29.0/private/service_contexts
cp system/sepolicy/private/seapp_contexts system/sepolicy/prebuilts/api/29.0/private/seapp_contexts
cp system/sepolicy/private/compat/28.0/28.0.ignore.cil system/sepolicy/prebuilts/api/29.0/private/compat/28.0/28.0.ignore.cil
cp system/sepolicy/private/compat/27.0/27.0.ignore.cil system/sepolicy/prebuilts/api/29.0/private/compat/27.0/27.0.ignore.cil
cp system/sepolicy/private/compat/26.0/26.0.ignore.cil system/sepolicy/prebuilts/api/29.0/private/compat/26.0/26.0.ignore.cil
cp system/sepolicy/public/servicemanager.te system/sepolicy/prebuilts/api/29.0/public/servicemanager.te 
cp system/sepolicy/private/untrusted_app.te system/sepolicy/prebuilts/api/29.0/private/untrusted_app.te
cp system/sepolicy/private/compat/28.0/28.0.cil system/sepolicy/prebuilts/api/29.0/private/compat/28.0/28.0.cil
cp system/sepolicy/private/system_server.te system/sepolicy/prebuilts/api/29.0/private/system_server.te 
