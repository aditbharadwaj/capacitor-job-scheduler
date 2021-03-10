

declare module '@capacitor/core' {
  interface PluginRegistry {
    CapacitorJobScheduler: CapacitorJobSchedulerPlugin;
  }
}

export interface CallbackError extends Error {
  code?: string;
}

export interface CapacitorJobSchedulerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  startForegroundServiceAboveOreo(ContentTitle:string,ContentText:string):Promise<void>;
  cancelJob(): Promise<void>;
}
