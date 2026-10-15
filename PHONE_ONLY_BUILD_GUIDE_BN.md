# Ucchash I&SC Billing System Automation — মোবাইল দিয়ে APK তৈরি

এই প্রজেক্টটি Android WebView wrapper। ওয়েবসাইট: https://ictisc-ivoice.netlify.app/

## গুরুত্বপূর্ণ
- এই ZIP-এর মধ্যে তৈরি APK নেই; APK GitHub Actions-এ বিল্ড করতে হবে।
- GitHub-এর মাধ্যমে বিল্ড করতে ইন্টারনেট এবং একটি GitHub account লাগবে।
- AAB workflow artifact-টি unsigned release bundle; Play Store-এ দেওয়ার আগে release signing, app ownership/brand permission, privacy policy, এবং Google Play policy requirements পূরণ করতে হবে।
- WebView-তে শুধু সাইট দেখানো যথেষ্ট নাও হতে পারে Play Store approval-এর জন্য।

## শুধু Android ফোন ব্যবহার করে APK তৈরি
1. এই ZIP ফাইলটি ফোনে ডাউনলোড করুন।
2. Chrome-এ github.com খুলে লগইন/অ্যাকাউন্ট তৈরি করুন।
3. `+` → **New repository** চাপুন। নাম দিন `ucchash-billing-app`; Public বা Private যেকোনোটি বেছে **Create repository** করুন।
4. রিপোজিটরিতে **Add file → Upload files** চাপুন। ZIP সরাসরি আপলোড করবেন না: ফোনের Files অ্যাপ দিয়ে ZIP extract করুন, তারপর ভেতরের `ucchashapp` ফোল্ডারের *ভেতরের সব ফাইল ও ফোল্ডার* রিপোজিটরির root-এ আপলোড করুন। `.github`-এর মতো hidden folder আপলোড হচ্ছে কি না যাচাই করুন।
5. **Commit changes** চাপুন।
6. রিপোজিটরিতে **Actions** tab খুলুন। `Build Android APK and AAB` workflow নির্বাচন করে **Run workflow** চাপুন।
7. রান শেষ হলে সবুজ ✓ হয়েছে কি না দেখুন। রানটি খুলে নিচে **Artifacts** অংশ থেকে `ucchash-android-builds` ডাউনলোড করুন।
8. Artifact ZIP extract করুন। `app-debug.apk` ফোনে ইনস্টল করে পরীক্ষা করুন। ফোন চাইলে Settings থেকে ওই ফাইল ম্যানেজার/ব্রাউজারের **Install unknown apps** অনুমতি দিন—শুধু নিজের তৈরি/বিশ্বস্ত APK-এর জন্য।

## যদি বিল্ড ব্যর্থ হয়
Actions-এর ব্যর্থ রান খুলে error log-এর শেষের অংশ কপি করে সহায়তার জন্য পাঠান। GitHub-এর ওয়েব Upload UI-তে অনেক ফাইল একসঙ্গে আপলোড করা অসুবিধাজনক হতে পারে; সে ক্ষেত্রে কম্পিউটার বা GitHub Codespaces ব্যবহার সহজতর।

## Play Store প্রকাশ
Workflow-এর release AAB-টি unsigned। Google Play-এ প্রকাশের আগে release keystore/signing, package name যাচাই, privacy policy, app content declarations, screenshots, এবং বর্তমান Play Console requirements সম্পন্ন করতে হবে। Google Play approval নিশ্চিত নয়।
